package blogwind.com.blogweb

import io.micronaut.http.HttpResponse
import io.micronaut.http.MutableHttpRequest
import io.micronaut.http.annotation.FilterMatcher
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.RequestAttribute
import io.micronaut.http.client.annotation.Client
import io.micronaut.http.filter.ClientFilterChain
import io.micronaut.http.filter.HttpClientFilter
import io.reactivex.Single
import org.reactivestreams.Publisher
import javax.inject.Singleton

@Client("\${blogapi.backend.url}")
interface BackendApi : UserAPI {
    @Get(value = "/blog?id={blogId}")
    fun blog(blogId: Int): Single<Blog>

    @Get(value = "/home")
    fun home(@RequestAttribute("X-User") blog: Blog): Single<String>
}

@UserAuth
interface UserAPI {
}

@UserAuth
@Singleton
class UserAuthClientFilter : HttpClientFilter {
    override fun doFilter(request: MutableHttpRequest<*>, chain: ClientFilterChain): Publisher<out HttpResponse<*>> {
        val blog = request.getAttribute("X-User", Blog::class.java)
        if (blog.isPresent) {
            request.header("X-Title", blog.get().title)
        }

        return chain.proceed(request)
    }
}

@FilterMatcher
@MustBeDocumented
@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.CLASS, AnnotationTarget.VALUE_PARAMETER)
annotation class UserAuth

data class Blog(val blogId: Int, val userId: Int, val title: String, val content: String)

package blogwind.com.blogweb

import io.micronaut.http.annotation.Get
import io.micronaut.http.client.annotation.Client
import io.reactivex.Single

@Client("\${blogapi.backend.url}")
interface BackendApi {
    @Get(value = "/blog?id={blogId}")
    fun blog(blogId: Int): Single<Blog>
}

data class Blog (val blogId:Int, val userId: Int, val title: String, val content: String)

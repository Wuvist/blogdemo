package blogwind.com.blogapi

import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.PathVariable
import io.reactivex.Single

@Controller("/blog")
class BlogController(private val backendApi: BackendApi) {
    @Get("/{id}")
    fun get(@PathVariable id: Int): Single<String> {
        return backendApi.blog(id).map {
            "<htm><body>" +
                    "<h1>${it.title}</h1>" +
                    "<h2>${getUsername(it.userId)}</h2>" +
                    "<p>${it.content}</p>" +
                    "</body></html>"
        }
    }

    private val usernames = mapOf<Int, String>(
            1 to "John Doe"
    )

    private fun getUsername(userId: Int): String {
        return usernames.getValue(userId)
    }
}

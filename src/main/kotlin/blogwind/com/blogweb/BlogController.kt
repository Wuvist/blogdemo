package blogwind.com.blogweb

import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.PathVariable
import io.reactivex.Single

@Controller("/blog")
class BlogController(private val backendApi: BackendApi, val usernameService: UsernameService) {
    @Get("/{id}")
    fun get(@PathVariable id: Int): Single<String> {
        val result = backendApi.blog(id)
        result.onErrorReturnItem(Blog(0, 0, "", ""))

        return result.flatMap {
            usernameService.getUsername(it.userId).map { username ->
                "<htm><body>" +
                        "<h1>${it.title}</h1>" +
                        "<h2>${username}</h2>" +
                        "<p>${it.content}</p>" +
                        "</body></html>"
            }
        }.onErrorReturnItem("error")
    }
}

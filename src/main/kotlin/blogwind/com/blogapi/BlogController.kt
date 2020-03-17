package blogwind.com.blogapi

import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.PathVariable
import io.reactivex.Single

@Controller("/blog")
class BlogController(private val backendApi: BackendApi, val usernameService: UsernameService) {
    @Get("/{id}")
    fun get(@PathVariable id: Int): Single<String> {
        return backendApi.blog(id).map {
            "<htm><body>" +
                    "<h1>${it.title}</h1>" +
                    "<h2>${usernameService.getUsername(it.userId)}</h2>" +
                    "<p>${it.content}</p>" +
                    "</body></html>"
        }
    }


}

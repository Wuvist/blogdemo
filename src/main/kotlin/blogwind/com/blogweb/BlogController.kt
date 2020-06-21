package blogwind.com.blogweb

import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.PathVariable

@Controller("/blog")
class BlogController(private val blogService: BlogService, val usernameService: UsernameServiceAsync) {
    @Get("/{id}")
    suspend fun get(@PathVariable id: Int): String {
        val blog = try {
            blogService.blog(id)
        } catch (e: Exception) {
            Blog(0, 0, "", null, "")
        }

        try {
            val username = usernameService.getUsername(blog.userId)
            return "<htm><body>" +
                    "<h1>${blog.title}</h1>" +
                    "<h2>${username}</h2>" +
                    "<p>${blog.content}</p>" +
                    "</body></html>"
        } catch (e: Exception) {
            return "error"
        }
    }
}

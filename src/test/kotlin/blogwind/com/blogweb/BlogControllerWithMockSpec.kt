package blogwind.com.blogweb

import com.blogwind.easywebmock.MockServerManager
import io.kotlintest.shouldBe
import io.kotlintest.specs.StringSpec
import io.micronaut.http.client.HttpClient
import io.micronaut.http.client.annotation.Client
import io.micronaut.test.annotation.MicronautTest
import io.micronaut.test.annotation.MockBean
import io.micronaut.test.extensions.kotlintest.MicronautKotlinTestExtension.getMock
import io.micronaut.test.support.TestPropertyProvider
import io.mockk.every
import io.mockk.mockk
import io.reactivex.Single
import java.security.InvalidParameterException
import javax.inject.Inject

@MicronautTest
class BlogControllerWithMockSpec : StringSpec(), TestPropertyProvider {
    @Inject
    lateinit var usernameService: UsernameService

    @Inject
    @field:Client("/")
    lateinit var client: HttpClient

    init {
        "test get blog via mock bean" {
            MockServerManager.setOneTimeResponseJson("/blog",
                    Blog(1, 2, "title", "bingo"))

            val mock = getMock(usernameService)
            every { mock.getUsername(any()) } returns Single.just("Jane Doe")

            var rsp: String = client.toBlocking().retrieve("/blog/1")
            rsp shouldBe "<htm><body><h1>title</h1><h2>Jane Doe</h2><p>bingo</p></body></html>"
        }

        "test error" {
            MockServerManager.setOneTimeResponseJson("/blog",
                    Blog(1, 2, "title", "bingo"))

            val mock = getMock(usernameService)
            every { mock.getUsername(any()) } throws InvalidParameterException("mio")

            var rsp: String = client.toBlocking().retrieve("/blog/1")
            rsp shouldBe "error"
        }
    }

    override fun getProperties(): MutableMap<String, String> {
        return mutableMapOf(
                "blogapi.backend.url" to MockServerManager.getUrl()
        )
    }

    @MockBean(UsernameService::class)
    fun usernameService(): UsernameService {
        return mockk()
    }
}


package blogwind.com.blogweb

import com.blogwind.easywebmock.MockServerManager
import io.kotlintest.matchers.string.shouldContain
import io.kotlintest.shouldBe
import io.kotlintest.shouldThrow
import io.kotlintest.specs.StringSpec
import io.micronaut.http.client.HttpClient
import io.micronaut.http.client.annotation.Client
import io.micronaut.http.client.exceptions.HttpClientResponseException
import io.micronaut.test.annotation.MicronautTest
import io.micronaut.test.support.TestPropertyProvider
import javax.inject.Inject

@MicronautTest
class BlogControllerSpec : StringSpec(), TestPropertyProvider {
    @Inject
    lateinit var config: Config

    @Inject
    @field:Client("/")
    lateinit var client: HttpClient

    init {
        "test overriding config value from application file" {
            config.backend.url shouldBe MockServerManager.getUrl()
        }

        "test get blog" {
            MockServerManager.setOneTimeResponseJson("/blog",
                    Blog(1, 1, "title", "bingo"))

            var rsp: String = client.toBlocking().retrieve("/blog/1")
            rsp shouldBe "<htm><body><h1>title</h1><h2>John Doe</h2><p>bingo</p></body></html>"
        }

        "test get blog error" {
            MockServerManager.setOneTimeResponseJson("/blog",
                    Blog(1, 2, "title", "bingo"))

            val exception = shouldThrow<HttpClientResponseException> {
                client.toBlocking().retrieve("/blog/1")
            }

            exception.message shouldContain "Key 2 is missing in the map"
        }
    }

    override fun getProperties(): MutableMap<String, String> {
        return mutableMapOf(
                "blogapi.backend.url" to MockServerManager.getUrl()
        )
    }
}

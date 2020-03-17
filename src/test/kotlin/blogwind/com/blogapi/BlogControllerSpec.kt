package blogwind.com.blogapi

import com.blogwind.easywebmock.MockServerManager
import io.kotlintest.shouldBe
import io.kotlintest.specs.StringSpec
import io.micronaut.test.annotation.MicronautTest
import io.micronaut.test.support.TestPropertyProvider
import javax.inject.Inject

@MicronautTest
class BlogControllerSpec : StringSpec(), TestPropertyProvider {
    @Inject
    lateinit var config: Config

    init {
        "test overriding config value from application file" {
            config.backend.url shouldBe MockServerManager.getUrl()
        }
    }

    override fun getProperties(): MutableMap<String, String> {
        return mutableMapOf(
                "blogapi.backend.url" to MockServerManager.getUrl()
        )
    }
}

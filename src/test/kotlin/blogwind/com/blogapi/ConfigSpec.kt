package blogwind.com.blogapi

import io.kotlintest.shouldBe
import io.kotlintest.specs.StringSpec
import io.micronaut.test.annotation.MicronautTest
import javax.inject.Inject

@MicronautTest
class ConfigSpec : StringSpec() {
    @Inject
    lateinit var config: Config

    init {
        "test config value from config file" {
            config.backend.url shouldBe "http://localhost:8000"
        }
    }
}

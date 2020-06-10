package blogwind.com.blogweb

import io.kotlintest.shouldBe
import io.kotlintest.specs.StringSpec
import io.micronaut.test.annotation.MicronautTest
import javax.inject.Inject

@MicronautTest(environments = ["dbmock"])
class ConfigSpec : StringSpec() {
    @Inject
    lateinit var blogRepo: BlogRepo

    init {
        "test db" {
            blogRepo.count() shouldBe 0

            var blog = Blog(0, 0, "foo", "bar")
            blogRepo.save(blog)
            blogRepo.count() shouldBe 1
        }
    }
}

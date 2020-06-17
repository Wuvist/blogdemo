package blogwind.com.blogweb

import io.kotlintest.shouldBe
import io.kotlintest.specs.StringSpec
import io.micronaut.test.annotation.MicronautTest
import javax.inject.Inject
import javax.sql.DataSource

@MicronautTest
class DBSpec : StringSpec() {
    @Inject
    lateinit var dataSource: DataSource

    init {
        "test db" {
            dataSource.connection.schema shouldBe "BLOGWIND"
        }
    }
}
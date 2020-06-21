package blogwind.com.blogweb

import io.kotlintest.shouldBe
import io.kotlintest.specs.StringSpec
import io.micronaut.context.annotation.Replaces
import io.micronaut.data.jdbc.annotation.JdbcRepository
import io.micronaut.data.model.query.builder.sql.Dialect
import io.micronaut.test.annotation.MicronautTest
import javax.inject.Inject
import javax.sql.DataSource

@JdbcRepository(dialect = Dialect.H2)
@Replaces(BlogRepo::class)
interface BlogTestRepo : BlogRepo {}

@MicronautTest(environments = ["dbmock"])
class DbMockSpec : StringSpec() {
    @Inject
    lateinit var dataSource: DataSource

    @Inject
    lateinit var blogRepo: BlogRepo

    init {
        "test db" {
            dataSource.connection.schema shouldBe "PUBLIC"
        }

        "test blog" {
            blogRepo.count() shouldBe 0

            val blog = Blog(1, 1, "title", null, "body")
            blogRepo.save(blog)
            blogRepo.count() shouldBe 1
        }
    }
}

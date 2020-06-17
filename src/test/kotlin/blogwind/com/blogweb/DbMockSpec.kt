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
@Replaces(AuditLogRepo::class)
interface AuditLogTestRepo : AuditLogRepo {}

@MicronautTest(environments = ["dbmock"])
class DbMockSpec : StringSpec() {
    @Inject
    lateinit var auditStatusRepo: AuditStatusRepo

    @Inject
    lateinit var auditLogRepo: AuditLogRepo

    @Inject
    lateinit var dataSource: DataSource

    init {
        "test db" {
            dataSource.connection.schema shouldBe "PUBLIC"
        }

        "test log" {
            auditLogRepo.count() shouldBe 0

            val log = AuditLog(1, 1, null, null, null, " ", "", 1)
            auditLogRepo.save(log)
            auditLogRepo.count() shouldBe 1
        }

        "test status" {
            auditStatusRepo.count() shouldBe 0
            var status = AuditStatus(1, "ok")
            auditStatusRepo.save(status)
            auditStatusRepo.count() shouldBe 1
            status = AuditStatus(2, "fail")
            auditStatusRepo.save(status)
            auditStatusRepo.count() shouldBe 2

            status = auditStatusRepo.findByName("ok")
            status.statusId shouldBe 1

            status = auditStatusRepo.findByName("fail")
            status.statusId shouldBe 2
        }
    }
}

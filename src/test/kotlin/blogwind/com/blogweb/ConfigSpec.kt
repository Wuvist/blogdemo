package blogwind.com.blogweb

import io.kotlintest.shouldBe
import io.kotlintest.specs.StringSpec
import io.micronaut.test.annotation.MicronautTest
import java.util.*
import javax.inject.Inject
import javax.inject.Named
import javax.sql.DataSource

@MicronautTest(environments = ["dbmock"])
class ConfigSpec : StringSpec() {
    @Inject
    lateinit var bookRepo: BookRepo

    @Inject
    lateinit var auditStatusRepo: AuditStatusRepo

    @Inject
    lateinit var auditLogRepo: AuditLogRepo

    init {
        "test log" {
//            val log = AuditLog(1, 1, null, null, null, " ", "", 1)
//            val log = AuditLog(1, 1, null, null, " ", "", 1)
//            auditLogRepo.save(log)
            auditLogRepo.count() shouldBe 2
        }
//        "test status" {
//            auditStatusRepo.deleteAll()
//            auditStatusRepo.count() shouldBe 0
//            var status = AuditStatus(1, "ok")
//            auditStatusRepo.save(status)
//            auditStatusRepo.count() shouldBe 1
//            status = AuditStatus(2, "fail")
//            auditStatusRepo.save(status)
//            auditStatusRepo.count() shouldBe 2
//
//            status = auditStatusRepo.findByName("ok")
//            status.statusId shouldBe 1
//
//            status = auditStatusRepo.findByName("fail")
//            status.statusId shouldBe 2
//        }
//        "test db" {
//            bookRepo.count() shouldBe 0
//
//            var book = Book(0, 1, "foo", Date(), "")
//            bookRepo.save(book)
//            delay(2000L)
//            bookRepo.save(book)
//            bookRepo.count() shouldBe 2
//            val books = bookRepo.findByUserIdAndTitle(1, "foo")
//            books.count() shouldBe 2
//            for(b in books) {
//                println(b.blogId.toString() +": "+ b.createTs.time.toString())
//            }
//        }
    }
}

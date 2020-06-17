package blogwind.com.blogweb

import io.micronaut.data.annotation.DateCreated
import io.micronaut.data.annotation.DateUpdated
import io.micronaut.data.annotation.MappedEntity
import io.micronaut.data.annotation.MappedProperty
import io.micronaut.data.jdbc.annotation.JdbcRepository
import io.micronaut.data.model.query.builder.sql.Dialect
import io.micronaut.data.repository.CrudRepository
import java.time.LocalDateTime
import java.util.*
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

@Entity
data class Blog(@Id
                @GeneratedValue
                var blogId: Int?,
                val userId: Int,
                val title: String,
                val content: String)

@Entity
data class Book(@Id
                @GeneratedValue
                var blogId: Int?,
                val userId: Int,
                val title: String,
                @DateUpdated
                var createTs: Date,
                val content: String)

@Entity
@MappedEntity("AUDIT_LOG")
data class AuditLog(@Id
                    @GeneratedValue
                    @MappedProperty("TCS_AUDIT_LOG_ID")
                    var id: Long,

                    @MappedProperty("EVENT_TYPE_ID")
                    var eventTypeId: Int,

                    @MappedProperty("USER_INPUT")
                    var userInput: String?,

                    @MappedProperty("IP")
                    var ip: String?,

                    @DateCreated
                    @MappedProperty("CREATED_TS")
                    var createdTs: LocalDateTime?,

                    @MappedProperty("SESSION_ID")
                    var sessionId: String?,

                    @MappedProperty("USER_ID")
                    var userId: String?,

                    @MappedProperty("STATUS_ID")
                    var statusId: Int
)

@Entity
data class AuditStatus(@Id
                       var statusId: Int,
                       val name: String)

@JdbcRepository(dialect = Dialect.H2)
interface BookRepo : CrudRepository<Book, Int> {
    fun findByUserIdAndTitle(userId: Int, title: String): List<Book>
}

@JdbcRepository(dialect = Dialect.ORACLE)
interface AuditStatusRepo : CrudRepository<AuditStatus, Int> {
    fun findByName(name: String): AuditStatus
}

@JdbcRepository(dialect = Dialect.ORACLE)
interface AuditLogRepo : CrudRepository<AuditLog, Long> {
    fun findByIp(ip: String): List<AuditLog>
}

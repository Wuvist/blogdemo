package blogwind.com.blogweb

import io.micronaut.data.annotation.DateUpdated
import io.micronaut.data.jdbc.annotation.JdbcRepository
import io.micronaut.data.model.query.builder.sql.Dialect
import io.micronaut.data.repository.CrudRepository
import java.time.LocalDateTime
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

@Entity
data class Blog(@Id
                @GeneratedValue
                var blogId: Int?,
                val userId: Int,
                val title: String,
                @DateUpdated
                var createTs: LocalDateTime?,
                val content: String)

@JdbcRepository(dialect = Dialect.H2)
interface BlogRepo : CrudRepository<Blog, Int> {
    fun findByUserIdAndTitle(userId: Int, title: String): List<Blog>
}

package blogwind.com.blogweb

import com.zaxxer.hikari.HikariDataSource
import io.micronaut.configuration.jdbc.hikari.DatasourceConfiguration
import io.micronaut.context.annotation.EachBean
import io.micronaut.context.annotation.Factory
import io.micronaut.context.annotation.Replaces
import io.micronaut.context.annotation.Requires
import javax.sql.DataSource

@Factory
class DataSourceOracle(config: Config) {
    val mainDb = OracleProvider().CreateDB()

    @Replaces(DataSource::class)
    @EachBean(DatasourceConfiguration::class)
    @Requires(property = "datasources.default.customerProvider", value = "true")
    fun GetDataSource(): DataSource {
        return mainDb
    }
}

class OracleProvider {
    fun CreateDB(): DataSource {
        val dataSource = HikariDataSource()
        dataSource.driverClassName = "oracle.jdbc.OracleDriver"
        dataSource.username = "BLOGWIND"
        dataSource.password = "blogwind"
        dataSource.jdbcUrl = "jdbc:oracle:thin:@localhost:1521:xe"

        return dataSource
    }
}
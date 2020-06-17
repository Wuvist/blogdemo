package blogwind.com.blogweb

import com.zaxxer.hikari.HikariDataSource
import io.micronaut.configuration.jdbc.hikari.DatasourceConfiguration
import io.micronaut.context.annotation.*
import io.micronaut.jdbc.BasicJdbcConfiguration
import io.micronaut.jdbc.CalculatedSettings
import javax.inject.Singleton
import javax.sql.DataSource

@Factory
class DataSourceOracle(config: Config) {
    val mainDb = OracleProvider().CreateDB()

//    @Context
//    @Replaces(DataSource::class)
    @EachBean(DatasourceConfiguration::class)
    @Primary
//    @Singleton
    @Requires(property = "datasources.default.customerProvider", value = "true")
    fun GetDataSource():DataSource {
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
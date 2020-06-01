package blogwind.com.blogweb

import io.micronaut.context.annotation.Requires
import io.micronaut.health.HealthStatus
import io.micronaut.management.endpoint.health.HealthEndpoint
import io.micronaut.management.health.indicator.AbstractHealthIndicator
import javax.inject.Singleton

@Singleton
@Requires(beans = [HealthEndpoint::class])
class ApiHealth : AbstractHealthIndicator<Map<String, Int>>() {
    override fun getName(): String {
        return "BackendApi"
    }

    override fun getHealthInformation(): Map<String, Int> {
        healthStatus = HealthStatus("Warning", null, true, 500)
        return mapOf("api" to 0)
    }
}

@Singleton
@Requires(beans = [HealthEndpoint::class])
class DbHealth : AbstractHealthIndicator<Map<String, Int>>() {
    override fun getName(): String {
        return "dbApi"
    }

    override fun getHealthInformation(): Map<String, Int> {
        healthStatus = HealthStatus.UP
        return mapOf("db" to 0)
    }
}

@Singleton
@Requires(beans = [HealthEndpoint::class])
class DiskHealth : AbstractHealthIndicator<Map<String, Int>>() {
    override fun getName(): String {
        return "diskApi"
    }

    override fun getHealthInformation(): Map<String, Int> {
        healthStatus = HealthStatus("Warning", null, true, 500)
        return mapOf("db" to 0)
    }
}


package blogwind.com.blogapi

import io.micronaut.context.annotation.ConfigurationBuilder
import io.micronaut.context.annotation.ConfigurationProperties

@ConfigurationProperties("blogapi")
class Config {
    var name: String = ""

    @ConfigurationBuilder("backend")
    var backend: BackEndHost = BackEndHost()
}

data class BackEndHost(var url: String = "")
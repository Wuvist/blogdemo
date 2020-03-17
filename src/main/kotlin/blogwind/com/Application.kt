package blogwind.com

import io.micronaut.runtime.Micronaut

object Application {

    @JvmStatic
    fun main(args: Array<String>) {
        Micronaut.build()
                .packages("blogwind.com")
                .mainClass(Application.javaClass)
                .start()
    }
}
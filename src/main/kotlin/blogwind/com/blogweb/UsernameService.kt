package blogwind.com.blogweb

import io.reactivex.Single
import javax.inject.Singleton

interface UsernameService {
    fun getUsername(userId: Int): Single<String>
}

@Singleton
class UsernameServiceImpl : UsernameService {
    private val usernames = mapOf<Int, String>(
            1 to "John Doe"
    )

    override fun getUsername(userId: Int): Single<String> {
        return Single.just(usernames.getValue(userId))
    }
}

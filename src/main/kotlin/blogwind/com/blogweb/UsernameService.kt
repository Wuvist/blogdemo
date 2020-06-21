package blogwind.com.blogweb

import io.reactivex.Single
import kotlinx.coroutines.rx2.await
import javax.inject.Singleton

interface UsernameService {
    fun getUsername(userId: Int): Single<String>
}

interface UsernameServiceAsync {
    suspend fun getUsername(userId: Int): String
}

@Singleton
class UsernameServiceAsyncImpl(val usernameService: UsernameService) : UsernameServiceAsync {
    override suspend fun getUsername(userId: Int): String {
        return usernameService.getUsername(userId).await()
    }
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

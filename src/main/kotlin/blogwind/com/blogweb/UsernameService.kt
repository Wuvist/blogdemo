package blogwind.com.blogweb

import javax.inject.Singleton

interface UsernameService {
    fun getUsername(userId: Int): String
}

@Singleton
class UsernameServiceImpl : UsernameService {
    private val usernames = mapOf<Int, String>(
            1 to "John Doe"
    )

    override fun getUsername(userId: Int): String {
        return usernames.getValue(userId)
    }
}

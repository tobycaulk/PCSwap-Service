package pcswapservice.service.da.mongo.user

import org.springframework.data.mongodb.repository.MongoRepository
import pcswapobjects.user.User

interface UserRepository: MongoRepository<User, String> {
    fun findByEmail(email: String): User?
    fun findByUsername(username: String): User?
    fun findByUserId(userId: String): User?
}
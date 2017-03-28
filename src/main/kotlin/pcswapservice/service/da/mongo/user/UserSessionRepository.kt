package pcswapservice.service.da.mongo.user

import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository
import pcswapservice.model.user.UserSession

@Repository
interface UserSessionRepository: MongoRepository<UserSession, String> {
    fun findByUserId(userId: String): UserSession?
    fun findBySessionId(sessionId: String): UserSession?
}
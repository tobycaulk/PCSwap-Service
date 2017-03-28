package pcswapservice.service.user

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import pcswapservice.model.request.user.CreateUserRequest
import pcswapservice.model.request.user.UserLoginRequest
import pcswapservice.model.response.user.CreateUserResponse
import pcswapservice.model.response.user.CreateUserResponseStatus
import pcswapservice.model.response.user.UserLoginResponse
import pcswapservice.model.response.user.UserLoginResponseStatus
import pcswapservice.model.user.User
import pcswapservice.model.user.UserAccountType
import pcswapservice.model.user.UserSession
import pcswapservice.service.da.mongo.user.UserRepository
import pcswapservice.service.da.mongo.user.UserSessionRepository
import pcswapservice.service.utilities.generateUUID
import pcswapservice.service.utilities.hashPassword
import pcswapservice.service.utilities.validatePassword
import java.util.*
import java.util.concurrent.TimeUnit

@Service
class UserService @Autowired constructor(var userRepository: UserRepository, var userSessionRepository: UserSessionRepository) {

    //1 MINUTE x 60
    val sessionTimeoutDelay = 60 * 60

    fun userLogin(request: UserLoginRequest): UserLoginResponse {
        var sessionId = ""
        var status = UserLoginResponseStatus.FAILURE

        var user: User? = userRepository.findByEmail(request.email)
        if(user != null) {
            if(validatePassword(request.password, user.password)) {
                sessionId = createUserSession(user.userId)
                status = UserLoginResponseStatus.SUCCESS
            } else {
                status = UserLoginResponseStatus.INVALID_PASSWORD
            }
        } else {
            status = UserLoginResponseStatus.INVALID_EMAIL
        }

        return UserLoginResponse(sessionId, status)
    }

    fun createUser(request: CreateUserRequest): CreateUserResponse {
        var userId: String = ""
        var status: CreateUserResponseStatus = CreateUserResponseStatus.FAILURE

        if(userRepository.findByEmail(request.email) != null || userRepository.findByUsername(request.username) != null) {
            status = CreateUserResponseStatus.ALREADY_EXISTS
        } else {
            userId = generateUUID()
            var user = User(
                    id=null,
                    userId=userId,
                    rating=0,
                    username=request.username,
                    email=request.email,
                    password= hashPassword(request.password),
                    createDate=Date(),
                    userAccountType=UserAccountType.BASIC,
                    sellSwaps= arrayListOf<String>(),
                    offerSwaps=arrayListOf<String>(),
                    soldSwaps=arrayListOf<String>(),
                    boughtSwaps=arrayListOf<String>())
            userRepository.insert(user)
            status = if(userRepository.findByUserId(userId) != null) CreateUserResponseStatus.SUCCESS else CreateUserResponseStatus.FAILURE
        }

        return CreateUserResponse(userId, status)
    }

    fun createUserSession(userId: String): String {
        var existingUserSession: UserSession? = userSessionRepository.findByUserId(userId)
        if(existingUserSession != null && validUserSession(existingUserSession.sessionId)) {
            return existingUserSession.sessionId
        }

        var sessionId = generateUUID()
        var userSession = UserSession(
                id=null,
                sessionId=sessionId,
                userId=userId,
                createDate=Date(),
                updatedDate=Date(),
                timeoutDelay=sessionTimeoutDelay)
        userSessionRepository.insert(userSession)

        return if(userSessionRepository.findBySessionId(sessionId) != null) sessionId else ""
    }

    fun validUserSession(sessionId: String): Boolean {
        var valid = false

        var userSession = userSessionRepository.findBySessionId(sessionId)
        if(userSession != null) {
            var calendar = Calendar.getInstance()
            calendar.setTime(userSession.updatedDate)
            calendar.add(Calendar.SECOND, userSession.timeoutDelay)
            var timeoutDate = calendar.time
            if(Date().before(timeoutDate)) {
                valid = true
                userSession.updatedDate = Date()
                userSessionRepository.save(userSession)
            } else {
                userSessionRepository.delete(userSession)
            }
        }

        return valid
    }
}
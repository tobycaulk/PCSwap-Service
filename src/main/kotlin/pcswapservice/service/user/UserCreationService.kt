package pcswapservice.service.user

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import pcswapservice.model.request.user.CreateUserRequest
import pcswapservice.model.response.user.CreateUserResponse
import pcswapservice.model.response.user.CreateUserResponseStatus
import pcswapservice.model.user.User
import pcswapservice.service.da.mongo.user.UserRepository
import pcswapservice.service.utilities.generateUUID
import pcswapservice.service.utilities.hashPassword
import java.util.*

@Service
class UserCreationService @Autowired constructor(var userRepository: UserRepository){

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
                    password=hashPassword(request.password),
                    createDate=Date())
            userRepository.insert(user)
            status = if(userRepository.findByUserId(userId) != null) CreateUserResponseStatus.SUCCESS else CreateUserResponseStatus.FAILURE
        }

        return CreateUserResponse(userId, status)
    }
}
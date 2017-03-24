package pcswapservice.controller.user

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import pcswapservice.controller.processRequest
import pcswapservice.model.request.RequestBase
import pcswapservice.model.request.user.CreateUserRequest
import pcswapservice.model.response.user.CreateUserResponse
import pcswapservice.service.user.UserCreationService

@RestController
class UserController @Autowired constructor(var userCreationService: UserCreationService){

    @PostMapping("/createUser")
    fun createUser(@RequestBody request: RequestBase<CreateUserRequest>) =
        processRequest<CreateUserRequest, CreateUserResponse>(request, { userCreationService.createUser(request.payload) })
}
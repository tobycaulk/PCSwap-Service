package pcswapservice.controller.user

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import pcswapservice.controller.ControllerUtil
import pcswapservice.model.request.RequestBase
import pcswapservice.model.request.user.CreateUserRequest
import pcswapservice.model.request.user.UserLoginRequest
import pcswapservice.model.response.user.CreateUserResponse
import pcswapservice.model.response.user.UserLoginResponse
import pcswapservice.service.user.UserService

@RestController
class UserController @Autowired constructor(var userService: UserService, var util: ControllerUtil) {

    @PostMapping("/createUser")
    fun createUser(@RequestBody request: RequestBase<CreateUserRequest>) =
        util.processRequest<CreateUserRequest, CreateUserResponse>(request, { userService.createUser(request.payload) })

    @PostMapping("/userLogin")
    fun userLogin(@RequestBody request: RequestBase<UserLoginRequest>) =
        util.processRequest<UserLoginRequest, UserLoginResponse>(request, { userService.userLogin(request.payload) })
}
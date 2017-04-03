package pcswapservice.controller.user

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpMethod
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*
import pcswapservice.controller.ControllerUtil
import pcswapservice.service.user.UserService
import pcswapobjects.request.user.*
import pcswapobjects.response.user.*
import pcswapobjects.request.*

@RestController
class UserController @Autowired constructor(var userService: UserService, var util: ControllerUtil) {

    @RequestMapping(value="/createUser")
    fun createUser(@RequestBody request: RequestBase<CreateUserRequest>) =
        util.processRequest<CreateUserRequest, CreateUserResponse>(request, { userService.createUser(request.payload) })

    @PostMapping("/userLogin")
    fun userLogin(@RequestBody request: RequestBase<UserLoginRequest>) =
        util.processRequest<UserLoginRequest, UserLoginResponse>(request, { userService.userLogin(request.payload) })

    @PostMapping("/getUser")
    fun getUser(@RequestBody request: RequestBase<GetUserRequest>) =
            util.processRequest<GetUserRequest, GetUserResponse>(request, { userService.getUser(request.payload) })

    @PostMapping("/getUserSession")
    fun getUserSession(@RequestBody request: RequestBase<GetUserSessionRequest>) =
            util.processRequest<GetUserSessionRequest, GetUserSessionResponse>(request, { userService.getUserSession(request.payload) })

    @PostMapping("/logout")
    fun logout(@RequestBody request: RequestBase<LogoutRequest>) =
            util.processRequest<LogoutRequest, LogoutResponse>(request, { userService.logout(request.payload) })
}
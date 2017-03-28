package pcswapservice.controller

import org.apache.log4j.Logger
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import pcswapservice.model.error.PCSwapError
import pcswapservice.model.error.getError
import pcswapservice.model.request.NoSessionPayload
import pcswapservice.model.request.RequestBase
import pcswapservice.model.response.ResponseBase
import pcswapservice.service.user.UserService
import pcswapservice.model.error.PCSwapError.ServiceError

@Component
class ControllerUtil @Autowired constructor(var userService: UserService) {
    val log = Logger.getLogger(ControllerUtil::class.java)

    fun <T, R> processRequest(request: RequestBase<T>, serviceFun: (T) -> R): ResponseBase<R?> {
        log.debug("Processing request with SessionId[${request.sessionId}]")

        var error: PCSwapError? = null

        var r: R? = null
        try {
            if(validSession(request.payload, request.sessionId)) {
                r = serviceFun(request.payload)
            } else {
                error = getError(ServiceError.INVALID_SESSION)
            }
        } catch(e: Exception) {
            log.error("Error while processing request", e)
            error = getError(ServiceError.UNHANDLED_EXCEPTION)
        }

        return ResponseBase(sessionId=request.sessionId, payload=r, error=error)
    }

    fun <T> validSession(t: T, sessionId: String): Boolean {
        var validSession = true

        try {
            var noSessionPayload = t is NoSessionPayload
            if (!noSessionPayload) {
                validSession = userService.validUserSession(sessionId)
            }
        } catch(e: Exception) {
            validSession = false;
            log.error("Error while validating session[${sessionId}]", e)
        }

        return validSession
    }
}
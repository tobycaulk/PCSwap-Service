package pcswapservice.controller

import org.apache.log4j.Logger
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import pcswapobjects.error.PCSwapError
import pcswapobjects.error.getError
import pcswapobjects.request.NoSessionPayload
import pcswapobjects.request.RequestBase
import pcswapobjects.response.ResponseBase
import pcswapservice.service.user.UserService

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
                error = getError(PCSwapError.ServiceError.INVALID_SESSION)
            }
        } catch(e: Exception) {
            log.error("Error while processing request", e)
            error = getError(PCSwapError.ServiceError.UNHANDLED_EXCEPTION)
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
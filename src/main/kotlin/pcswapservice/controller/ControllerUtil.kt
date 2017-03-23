package pcswapservice.controller

import org.apache.log4j.Logger
import pcswapservice.model.request.RequestBase
import pcswapservice.model.response.ResponseBase

class ControllerUtil

fun <T, R> processRequest(t: RequestBase<T>, serviceFun: (T) -> R): ResponseBase<R?> {
    var log = Logger.getLogger(ControllerUtil::class.java)

    log.debug("Processing request with SessionId[${t.sessionId}]")

    var errorNumber: Int? = 0
    var errorDescription: String? = ""

    var r: R? = null
    try {
        r = serviceFun(t.payload)
    } catch(e: Exception) {
        log.error("Error while processing request", e)
        errorNumber = 1
        errorDescription = "Internal error"
    }

    return ResponseBase(sessionId=t.sessionId, payload=r, errorNumber=errorNumber, errorDescription=errorDescription)
}
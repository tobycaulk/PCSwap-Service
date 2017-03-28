package pcswapservice.model.error

import com.fasterxml.jackson.annotation.JsonProperty

class PCSwapError(@JsonProperty("ErrorNumber") var errorNumber: Int,
            @JsonProperty("ErrorDescription") var errorDescription: String) {

    enum class ServiceError(var errorNumber: Int, var errorDescription: String) {
        UNHANDLED_EXCEPTION(1, "Internal server error"),
        INVALID_SESSION(2, "Invalid session")
    }
}

fun getError(error: PCSwapError.ServiceError) = PCSwapError(error.errorNumber, error.errorDescription)
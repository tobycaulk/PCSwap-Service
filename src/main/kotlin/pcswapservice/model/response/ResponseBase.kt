package pcswapservice.model.response

import com.fasterxml.jackson.annotation.JsonProperty

data class ResponseBase<T>(
        @JsonProperty("SessionId") var sessionId: String,
        @JsonProperty("ErrorNumber") var errorNumber: Int? = 0,
        @JsonProperty("ErrorDescription") var errorDescription: String? = "",
        @JsonProperty("Payload") var payload: T)
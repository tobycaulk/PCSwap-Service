package pcswapservice.model.request

import com.fasterxml.jackson.annotation.JsonProperty

data class RequestBase<T>(@JsonProperty("SessionId") var sessionId: String, @JsonProperty("Payload") var payload: T)
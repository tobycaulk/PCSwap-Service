package pcswapservice.model.response.user

import com.fasterxml.jackson.annotation.JsonProperty

enum class UserLoginResponseStatus {
    SUCCESS,
    FAILURE,
    INVALID_PASSWORD,
    INVALID_EMAIL
}

data class UserLoginResponse(@JsonProperty("SessionId") var sessionId: String,
                             @JsonProperty("Status") var status: UserLoginResponseStatus)
package pcswapservice.model.response.user

import com.fasterxml.jackson.annotation.JsonProperty

enum class CreateUserResponseStatus {
    SUCCESS,
    FAILURE,
    ALREADY_EXISTS
}

data class CreateUserResponse(@JsonProperty("UserId") var userId: String,
                              @JsonProperty("Status") var status: CreateUserResponseStatus)
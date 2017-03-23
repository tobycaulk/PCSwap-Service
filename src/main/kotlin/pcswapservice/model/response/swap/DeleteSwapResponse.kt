package pcswapservice.model.response.swap

import com.fasterxml.jackson.annotation.JsonProperty

data class DeleteSwapResponse(@JsonProperty("Success") var success: Boolean)
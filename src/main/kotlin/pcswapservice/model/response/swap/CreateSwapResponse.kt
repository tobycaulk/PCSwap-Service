package pcswapservice.model.response.swap

import com.fasterxml.jackson.annotation.JsonProperty

data class CreateSwapResponse(@JsonProperty("SwapId") var swapId: String)
package pcswapservice.model.response.swap

import com.fasterxml.jackson.annotation.JsonProperty

enum class OfferSwapItemResponseStatus {
    SUCCESS,
    FAILURE,
    ALREADY_OFFERED
}

data class OfferSwapItemResponse(@JsonProperty("Status") var status: OfferSwapItemResponseStatus)
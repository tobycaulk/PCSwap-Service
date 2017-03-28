package pcswapservice.model.request.swap

import com.fasterxml.jackson.annotation.JsonProperty
import pcswapservice.model.swap.SwapItem

data class OfferSwapItemRequest(@JsonProperty("SwapId") var swapId: String,
                                @JsonProperty("SwapItem") var swapItem: SwapItem)
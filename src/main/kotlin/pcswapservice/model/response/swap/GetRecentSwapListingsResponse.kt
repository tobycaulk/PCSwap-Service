package pcswapservice.model.response.swap

import com.fasterxml.jackson.annotation.JsonProperty
import pcswapservice.model.swap.Swap

data class GetRecentSwapListingsResponse(@JsonProperty("SwapListings") var swapListings: List<Swap>?)
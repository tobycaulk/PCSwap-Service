package pcswapservice.model.request.swap

import com.fasterxml.jackson.annotation.JsonProperty
import pcswapservice.model.swap.SwapItem

data class CreateSwapRequest(
        @JsonProperty("SellItem") var sellItem: SwapItem,
        @JsonProperty("TradeForItems") var tradeForItems: List<SwapItem>,
        @JsonProperty("SellerUserId") var sellerUserId: String)
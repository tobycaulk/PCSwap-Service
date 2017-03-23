package pcswapservice.model.request.swap

import com.fasterxml.jackson.annotation.JsonProperty

data class GetSwapListingsBySellerRequest(@JsonProperty("SellerUserId") var sellerUserId: String)
package pcswapservice.model.swap

import com.fasterxml.jackson.annotation.JsonProperty
import org.springframework.data.annotation.Id
import org.springframework.data.annotation.PersistenceConstructor
import org.springframework.data.mongodb.core.mapping.Document
import java.util.*

@Document(collection="swap")
data class Swap @PersistenceConstructor constructor(
        @Id val id: String?,
        @JsonProperty("SwapId") var swapId: String?,
        @JsonProperty("SellItem") var sellItem: String,
        @JsonProperty("TradeForItems") var tradeForItems: MutableList<String>,
        @JsonProperty("OfferItems") var offerItems: MutableList<String>,
        @JsonProperty("SellerUserId") var sellerUserId: String,
        @JsonProperty("CreateDate") val createDate: Date)
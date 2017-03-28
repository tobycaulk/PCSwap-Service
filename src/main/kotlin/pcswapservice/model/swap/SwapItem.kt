package pcswapservice.model.swap

import com.fasterxml.jackson.annotation.JsonProperty
import org.springframework.data.annotation.Id
import org.springframework.data.annotation.PersistenceConstructor
import org.springframework.data.mongodb.core.mapping.Document
import pcswapservice.model.transaction.TransactionInformation

@Document(collection="swapItem")
data class SwapItem @PersistenceConstructor constructor(
        @Id var id: String?,
        @JsonProperty("SwapItemId") var swapItemId: String?,
        @JsonProperty("UserId") var userId: String,
        @JsonProperty("Item") var item: String,
        @JsonProperty("Description") var description: String,
        @JsonProperty("ImageUrl") var imageUrl: String,
        @JsonProperty("TransactionInformation") var transactionInformation: TransactionInformation)
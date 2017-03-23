package pcswapservice.model.swap

import com.fasterxml.jackson.annotation.JsonProperty
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import pcswapservice.model.transaction.TransactionInformation

data class SwapItem(
        @JsonProperty("Item") var item: String,
        @JsonProperty("Description") var description: String,
        @JsonProperty("ImageUrl") var imageUrl: String,
        @JsonProperty("TransactionInformation") var transactionInformation: TransactionInformation)
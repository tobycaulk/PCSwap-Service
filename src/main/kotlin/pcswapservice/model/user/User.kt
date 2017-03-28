package pcswapservice.model.user

import com.fasterxml.jackson.annotation.JsonProperty
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.util.*

@Document(collection="user")
data class User(
        @Id val id: String?,
        @JsonProperty("UserId") var userId: String,
        @JsonProperty("Rating") var rating: Int,
        @JsonProperty("Username") var username: String,
        @JsonProperty("Email") var email: String,
        @JsonProperty("Password") var password: String,
        @JsonProperty("CreateDate") var createDate: Date,
        @JsonProperty("UserAccountType") var userAccountType: UserAccountType,
        @JsonProperty("SellSwaps") var sellSwaps: MutableList<String>,
        @JsonProperty("OfferSwaps") var offerSwaps: MutableList<String>,
        @JsonProperty("SoldSwaps") var soldSwaps: MutableList<String>,
        @JsonProperty("BoughtSwaps") var boughtSwaps: MutableList<String>)
package pcswapservice.model.transaction

import com.fasterxml.jackson.annotation.JsonProperty
import pcswapservice.model.currency.CurrencyInformation

data class TransactionInformation(
        @JsonProperty("RawAmount") var rawAmount: Int,
        @JsonProperty("CurrencyInformation") var currencyInformation: CurrencyInformation)
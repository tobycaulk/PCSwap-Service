package pcswapservice.model.currency

import com.fasterxml.jackson.annotation.JsonProperty

data class CurrencyInformation(@JsonProperty("CurrencyCode") var currencyCode: Int)
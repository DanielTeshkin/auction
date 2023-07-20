package com.auction.mobile.data.model

import com.auction.mobile.domain.models.RacePriceModel
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class RacePriceDTO(
    @Json(name = "price")
    val price: String
)

fun RacePriceDTO.toModel(): RacePriceModel {
    return RacePriceModel(
        price = price
    )
}
fun RacePriceModel.toDTO(): RacePriceDTO {
        return RacePriceDTO(
            price = price
        )
}

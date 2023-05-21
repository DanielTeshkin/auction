package com.example.auctionapp.data.model

import com.example.auctionapp.domain.models.RacePriceModel
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

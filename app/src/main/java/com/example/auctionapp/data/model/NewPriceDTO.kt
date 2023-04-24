package com.example.auctionapp.data.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class NewPriceDTO(
    @Json(name = "price")
    val price: String
)
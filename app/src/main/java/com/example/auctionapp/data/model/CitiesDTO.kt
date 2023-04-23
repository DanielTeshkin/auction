package com.example.auctionapp.data.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CitiesDTO(
    @Json(name = "id")
    val id: String,
    @Json(name = "name")
    val name: String
)
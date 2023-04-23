package com.example.auctionapp.data.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SignUpResponseDTO(
    @Json(name = "access")
    val accessToken: String,
    @Json(name = "refresh")
    val refreshToken: String
)
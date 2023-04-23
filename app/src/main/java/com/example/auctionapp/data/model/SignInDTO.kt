package com.example.auctionapp.data.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SignInDTO(
    @Json(name = "username")
    val phone: String,
    @Json(name = "password")
    val code: String
)
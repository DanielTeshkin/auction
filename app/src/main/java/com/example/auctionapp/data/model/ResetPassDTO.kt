package com.example.auctionapp.data.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ResetPassDTO(
    @Json(name = "username")
    val userName: String,
    @Json(name = "password1")
    val password1: String,
    @Json(name = "password2")
    val password2: String
)

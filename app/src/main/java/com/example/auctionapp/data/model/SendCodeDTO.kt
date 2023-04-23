package com.example.auctionapp.data.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SendCodeDTO(
    @Json(name = "phone")
    val phone: String,
    @Json(name = "code")
    val code: String
)



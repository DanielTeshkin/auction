package com.example.auctionapp.data.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CodeResponseErrorDTO(
    @Json(name = "code")
    val code: String,
    @Json(name = "messege")
    val messege: String
)
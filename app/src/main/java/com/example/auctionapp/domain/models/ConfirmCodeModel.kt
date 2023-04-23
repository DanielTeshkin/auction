package com.example.auctionapp.domain.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ConfirmCodeModel(
    @Json(name = "key")
    val key: String = "phone",
    @Json(name = "value")
    val phone: String,
)

package com.example.auctionapp.domain.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SendCodeModel(
val phone: String,
val code: String
)
package com.auction.mobile.data.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SignInDTO(
    @Json(name = "username")
    val logo: String,
    @Json(name = "password")
    val pass: String
)
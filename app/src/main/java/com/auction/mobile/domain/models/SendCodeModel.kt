package com.auction.mobile.domain.models

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SendCodeModel(
val phone: String,
val code: String
)
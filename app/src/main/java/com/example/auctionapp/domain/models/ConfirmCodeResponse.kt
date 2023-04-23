package com.example.auctionapp.domain.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

data class ConfirmCodeResponse(
val phone: String,
val isAuthorized: Boolean
)

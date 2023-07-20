package com.auction.mobile.data.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class GetRefreshDTO(
    @Json(name = "refresh")
    val refresh: String
)
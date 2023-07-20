package com.auction.mobile.data.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
class GetRefreshResponseDTO(
    @Json(name = "access")
    val accessToken: String
)
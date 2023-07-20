package com.auction.mobile.data.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ProductToSetFavoriteDTO(
    @Json(name = "product")
    val id: String

)

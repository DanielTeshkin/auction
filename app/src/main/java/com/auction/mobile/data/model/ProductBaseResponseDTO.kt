package com.auction.mobile.data.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ProductBaseResponseDTO<out T : Any>(
    @Json(name = "count")
    val count: Int,
    @Json(name = "next")
    val next: Int?,
    @Json(name = "previous")
    val previous: Int?,
    @Json(name = "results")
    val results: List<T>
)
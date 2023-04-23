package com.example.auctionapp.data.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ProductBaseResponseDTO(
    @Json(name = "count")
    val count: Int,
    @Json(name = "next")
    val next: Int?,
    @Json(name = "previous")
    val previous: Int?,
    @Json(name = "results")
    val results: List<ProductDTO>
)
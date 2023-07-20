package com.auction.mobile.data.model

import com.auction.mobile.domain.models.BidCreateRequestModel
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class BidCreateRequestDTO(
    @Json(name = "product") val productId: String
)

fun BidCreateRequestModel.toDTO(): BidCreateRequestDTO {
    return BidCreateRequestDTO(
        productId = productId
    )
}

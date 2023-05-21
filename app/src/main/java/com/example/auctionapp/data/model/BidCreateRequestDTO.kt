package com.example.auctionapp.data.model

import com.example.auctionapp.domain.models.BidCreateRequestModel
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

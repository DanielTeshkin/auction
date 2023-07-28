package com.auction.mobile.data.model

import com.auction.mobile.domain.models.ProductHistoryModel
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ProductHistoryDTO(
    @Json(name = "price")
    val price: Int,
    @Json(name = "date_created")
    val dateCreated: String,
    @Json(name = "user")
    val user: AuthorDTO
)


fun ProductHistoryDTO.toModel(): ProductHistoryModel {
    return ProductHistoryModel(
        price = price,
        dateCreated = dateCreated,
        user = user.toModel()
    )
}
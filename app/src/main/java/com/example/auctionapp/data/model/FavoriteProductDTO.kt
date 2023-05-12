package com.example.auctionapp.data.model

import com.example.auctionapp.domain.models.ElectedProductModel
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class FavoriteProductDTO(
    @Json(name = "id")
    val id: String,
    @Json(name = "user")
    val userInfo: GetUserInfoDTO,
    @Json(name = "notes")
    val notes: String? = null,
    @Json(name = "product")
    val product: ProductDTO
)


fun FavoriteProductDTO.toModel(): ElectedProductModel {
    return ElectedProductModel(
        id = id,
        userInfo = userInfo.toModel(),
        notes = notes,
        product = product.toModel()
    )
}
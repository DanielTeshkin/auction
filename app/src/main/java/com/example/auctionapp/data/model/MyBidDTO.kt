package com.example.auctionapp.data.model

import com.example.auctionapp.domain.models.MyBidModel
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class MyBidDTO(
    @Json(name = "id")
    val id: String,
    @Json(name = "user")
    val userInfo: GetUserInfoDTO,
    @Json(name = "status")
    val status: String,
    @Json(name = "product")
    val product: ProductDTO,

)

fun MyBidDTO.toModel(): MyBidModel {
    return MyBidModel(
        id = id,
        userInfo = userInfo.toModel(),
        status = status,
        product = product.toModel()
    )
}

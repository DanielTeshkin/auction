package com.auction.mobile.domain.models

data class MyBidModel(
    val id: String,
    val userInfo: GetUserInfoModel,
    val status: String,
    val product: ProductModel
)

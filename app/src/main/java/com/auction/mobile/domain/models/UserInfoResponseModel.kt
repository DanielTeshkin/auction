package com.auction.mobile.domain.models


data class UserInfoResponseModel(
    val firstName: String?,
    val lastName: String?,
    val patronymic: String?,
    val email: String,
    val cityPk: City,
)

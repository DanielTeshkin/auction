package com.auction.mobile.domain.models

data class UserInfoModel(
    val firstName: String?,
    val lastName: String?,
    val patronymic: String?,
    val cityPk: String,
    val email: String
)

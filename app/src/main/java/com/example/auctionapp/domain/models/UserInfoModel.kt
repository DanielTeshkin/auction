package com.example.auctionapp.domain.models

import com.squareup.moshi.Json

data class UserInfoModel(
    val firstName: String?,
    val lastName: String?,
    val patronymic: String?,
    val cityPk: String,
    val email: String
)

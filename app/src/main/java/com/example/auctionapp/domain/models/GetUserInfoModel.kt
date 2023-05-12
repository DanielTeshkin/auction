package com.example.auctionapp.domain.models

data class GetUserInfoModel(
    val phone: String,
    val firstName: String?,
    val lastName: String?,
    val patronymic: String?,
    val email: String,
    val city: City?
)

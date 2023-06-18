package com.example.auctionapp.domain.models


data class AuthorModel(
    val username: String,
    val firstname: String?,
    val lastname: String?,
    val patronymic: String?,
    val email: String?
): java.io.Serializable

package com.auction.mobile.domain.models


data class AuthorModel(
    val username: String,
    val firstname: String?,
    val lastname: String?,
    val patronymic: String?,
    val email: String?
): java.io.Serializable

package com.auction.mobile.data.model

import com.auction.mobile.domain.models.SignUpModel
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SignUpResponseDTO(
    @Json(name = "access")
    val accessToken: String,
    @Json(name = "refresh")
    val refreshToken: String
)

fun SignUpResponseDTO.toSignUpModel(): SignUpModel {
    return SignUpModel(
        accessToken = accessToken,
        refreshToken = refreshToken
    )
}


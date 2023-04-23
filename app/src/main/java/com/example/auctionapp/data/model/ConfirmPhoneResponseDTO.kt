package com.example.auctionapp.data.model

import com.example.auctionapp.domain.models.ConfirmCodeResponse
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ConfirmPhoneResponseDTO(
    @Json(name = "phone")
    val phone: String,
    @Json(name = "is_authorized")
    val isAuthorized: Boolean

)

fun ConfirmPhoneResponseDTO.toModel(): ConfirmCodeResponse {
    return ConfirmCodeResponse(
        phone = phone,
        isAuthorized = isAuthorized
    )
}
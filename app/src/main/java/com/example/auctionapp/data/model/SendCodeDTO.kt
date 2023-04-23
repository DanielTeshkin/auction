package com.example.auctionapp.data.model

import com.example.auctionapp.domain.models.SendCodeModel
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SendCodeDTO(
    @Json(name = "phone")
    val phone: String,
    @Json(name = "code")
    val code: String
)

fun SendCodeDTO.toModel() : SendCodeModel {
    return SendCodeModel(
        phone = phone,
        code = code
    )
}



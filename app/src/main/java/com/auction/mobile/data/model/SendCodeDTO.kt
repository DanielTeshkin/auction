package com.auction.mobile.data.model

import com.auction.mobile.domain.models.SendCodeModel
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



package com.auction.mobile.data.model

import com.auction.mobile.domain.models.ConfirmPhoneModel
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ConfirmPhoneDTO(
    @Json(name = "phone")
    val phone: String
)

fun ConfirmPhoneDTO.toModel(): ConfirmPhoneModel {
    return ConfirmPhoneModel(
        phone = phone
    )
}

    fun ConfirmPhoneModel.toDTO(): ConfirmPhoneDTO {
        return ConfirmPhoneDTO(
            phone = phone
        )
}
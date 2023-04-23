package com.example.auctionapp.data.model

import com.example.auctionapp.domain.models.ConfirmPhoneModel
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import okhttp3.internal.threadName

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
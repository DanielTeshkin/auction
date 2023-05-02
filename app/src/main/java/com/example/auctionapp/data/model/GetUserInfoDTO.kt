package com.example.auctionapp.data.model

import com.example.auctionapp.domain.models.GetUserInfoModel
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class GetUserInfoDTO(
    @Json(name = "username")
    val phone: String,
    @Json(name = "first_name")
    val firstName: String?,
    @Json(name = "last_name")
    val lastName: String?,
    @Json(name = "patronymic")
    val patronymic: String?,
    @Json(name = "email")
    val email: String,
    @Json(name = "city")
    val city: CityDTO
)

fun GetUserInfoDTO.toModel(): GetUserInfoModel {
    return GetUserInfoModel(
        phone = phone,
        firstName = firstName,
        lastName = lastName,
        patronymic = patronymic,
        email = email,
        city = city.toModel()
    )
}

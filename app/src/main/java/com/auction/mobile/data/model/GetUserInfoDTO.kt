package com.auction.mobile.data.model

import com.auction.mobile.domain.models.GetUserInfoModel
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
    val city: CityDTO? = null
)

fun GetUserInfoDTO.toModel(): GetUserInfoModel {
    return GetUserInfoModel(
        phone = phone,
        firstName = firstName,
        lastName = lastName,
        patronymic = patronymic,
        email = email,
        city = city?.toModel()
    )
}

package com.auction.mobile.data.model

import com.auction.mobile.domain.models.UserInfoModel
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class UserInfoDTO(
    @Json(name = "first_name")
    val firstName: String?,
    @Json(name = "last_name")
    val lastName: String?,
    @Json(name = "patronymic")
    val patronymic: String?,
    @Json(name = "city_pk")
    val cityPk: String,
    @Json(name = "email")
    val email: String
)

fun UserInfoDTO.toModel(): UserInfoModel {
    return UserInfoModel(
        firstName, lastName, patronymic, cityPk, email
    )
}

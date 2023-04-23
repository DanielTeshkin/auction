package com.example.auctionapp.data.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class UserInfoResponseDTO(
    @Json(name = "first_name")
    val firstName: String?,
    @Json(name = "last_name")
    val lastName: String?,
    @Json(name = "patronymic")
    val patronymic: String?,
    @Json(name = "email")
    val email: String,
    @Json(name = "city")
    val cityPk: CityDTO,
)
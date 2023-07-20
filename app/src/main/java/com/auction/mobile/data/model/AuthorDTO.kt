package com.auction.mobile.data.model

import com.auction.mobile.domain.models.AuthorModel
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class AuthorDTO(
    @Json(name = "username")
    val username: String,
    @Json(name = "first_name")
    val firstname: String?,
    @Json(name = "last_name")
    val lastname: String?,
    @Json(name = "patronymic")
    val patronymic: String?,
    @Json(name = "email")
    val email: String?
)

fun AuthorDTO.toModel(): AuthorModel {
    return AuthorModel(
        username, firstname, lastname, patronymic, email
    )
}

package com.example.auctionapp.data.model

import com.example.auctionapp.domain.models.City
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CityDTO(
    @Json(name = "id")
    val id: String,
    @Json(name = "name")
    val name: String,
    @Json(name = "alternate_names")
    val alternateNames: String,
    @Json(name = "display_name")
    val displayName: String
)

fun CityDTO.toModel(): City {
    return City(
        id, name, alternateNames, displayName
    )
}
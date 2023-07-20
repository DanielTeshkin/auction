package com.auction.mobile.data.model

import com.auction.mobile.domain.models.CitiesModel
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CitiesDTO(
    @Json(name = "id")
    val id: String,
    @Json(name = "name")
    val name: String
)

fun List<CitiesDTO>.toModel(): List<CitiesModel> {
    val newList = arrayListOf<CitiesModel>()
    this.forEach {
        newList.add(
            CitiesModel(
                id = it.id,
                name = it.name
            )
        )
    }
    return newList

}

fun CitiesDTO.toModel(): CitiesModel {
    return CitiesModel(
        id, name
    )
}
package com.auction.mobile.data.model

import com.auction.mobile.domain.models.PhotosModel
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PhotosDTO(
    @Json(name = "id")
    val id: String,
    @Json(name = "file")
    val file: String
)

fun List<PhotosDTO>.toModel(): List<PhotosModel> {
    val newList = arrayListOf<PhotosModel>()
    this.forEach {
        newList.add(
            PhotosModel(
                id = it.id,
                file = it.file
            )
        )
    }
    return newList
}
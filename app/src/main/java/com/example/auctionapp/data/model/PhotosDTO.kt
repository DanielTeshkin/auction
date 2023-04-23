package com.example.auctionapp.data.model

import com.example.auctionapp.domain.models.PhotosModel
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import okhttp3.internal.threadName

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
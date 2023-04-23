package com.example.auctionapp.domain.models

import com.example.auctionapp.data.model.PhotosDTO
import com.squareup.moshi.Json

data class ProductModel(
    val id: String,
    val city: CitiesModel?,
    val photos: List<PhotosModel>?,
    val title: String?,
    val description: String?,
    val startDate: String?,
    val endDate: String?,
    val price: Double,
    val category: String?
)

data class PhotosModel(
    val id: String,
    val file: String
)

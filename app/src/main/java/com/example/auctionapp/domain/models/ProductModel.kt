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
    val price: Long,
    val category: String?
)

data class PhotosModel(
    val id: String,
    val file: String
)

fun ProductModel.toFavoriteModel(isFavorite: Boolean): FavoriteProductModel {
    return FavoriteProductModel(
        id, city, photos, title, description, startDate, endDate, price, category, isFavorite
    )


}

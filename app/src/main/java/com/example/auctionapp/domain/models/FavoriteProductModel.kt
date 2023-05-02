package com.example.auctionapp.domain.models

data class FavoriteProductModel(
    val id: String,
    val city: CitiesModel?,
    val photos: List<PhotosModel>?,
    val title: String?,
    val description: String?,
    val startDate: String?,
    val endDate: String?,
    val price: Long,
    val category: String?,
    var isFavorite: Boolean
)

fun FavoriteProductModel.toProductModel(): ProductModel {
    return ProductModel(
        id, city, photos, title, description, startDate, endDate, price, category
    )
}

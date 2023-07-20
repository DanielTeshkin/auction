package com.auction.mobile.domain.models

data class ProductModel(
    val rateHikePrice: String,
    val endRegistration: String,
    val startRegistration: String,
    val id: String,
    val city: CitiesModel?,
    val photos: List<PhotosModel>?,
    val title: String?,
    val description: String?,
    val startDate: String?,
    val endDate: String?,
    val price: Long,
    val category: String?,
    val author: AuthorModel
): java.io.Serializable

data class PhotosModel(
    val id: String,
    val file: String
): java.io.Serializable

fun ProductModel.toFavoriteModel(isFavorite: Boolean): FavoriteProductModel {
    return FavoriteProductModel(
        startRegistration = startRegistration,
        endRegistration = endRegistration,
        id = id,
        city = city,
        photos = photos,
        title = title,
        description = description,
        startDate = startDate,
        endDate = endDate,
        price = price,
        category = category,
        isFavorite = isFavorite,
        rateHikePrice = rateHikePrice
    )


}

package com.auction.mobile.domain.models

data class FavoriteProductModel(
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
    var isFavorite: Boolean
)

fun FavoriteProductModel.toProductModel(): ProductModel {
    return ProductModel(
        rateHikePrice, endRegistration, startRegistration,id, city, photos, title, description, startDate, endDate, price, category,                 author = AuthorModel("", null,null,null,null,)

    )
}

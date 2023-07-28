package com.auction.mobile.domain.models

data class ElectedProductModel(
    val id: String,
    val userInfo: GetUserInfoModel,
    val notes: String? = null,
    val product: ProductModel
)

fun ElectedProductModel.toFavoriteModel(isFavorite: Boolean) : FavoriteProductModel {
    return FavoriteProductModel(
        id = id,
        city = product.city,
        photos = product.photos,
        title = product.title,
        description = product.description,
        startDate = product.startDate,
        endDate = product.endDate,
        price = product.price,
        category = product.category,
        isFavorite = isFavorite,
        startRegistration = product.startRegistration,
        endRegistration = product.endRegistration,
        rateHikePrice = product.rateHikePrice,
        registrationPrice = product.registrationPrice
    )
}

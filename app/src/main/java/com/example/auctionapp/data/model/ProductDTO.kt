package com.example.auctionapp.data.model

import com.example.auctionapp.domain.models.ProductModel
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ProductDTO(
    @Json(name = "rate_hike_price")
    val rateHikePrice: String,
    @Json(name = "end_registration_date")
    val endRegistration: String,
    @Json(name = "start_registration_date")
    val startRegistration: String,
    @Json(name = "id")
    val id: String,
    @Json(name = "city")
    val city: CitiesDTO?,
    @Json(name = "photos")
    val photos: List<PhotosDTO>?,
    @Json(name = "title")
    val title: String?,
    @Json(name = "description")
    val description: String?,
    @Json(name = "start_date")
    val startDate: String?,
    @Json(name = "end_date")
    val endDate: String?,
    @Json(name = "price")
    val price: Long,
    @Json(name = "category")
    val category: String?,
    @Json(name = "author")
    val author: AuthorDTO
)


fun List<ProductDTO>.toModel(): List<ProductModel> {
    val newList = arrayListOf<ProductModel>()
    this.forEach {
        newList.add(
            ProductModel(
                id = it.id,
                city = it.city?.toModel(),
                photos = it.photos?.toModel(),
                title = it.title,
                description = it.description,
                startDate = it.startDate,
                endDate = it.endDate,
                price = it.price,
                category = it.category,
                startRegistration = it.startRegistration,
                endRegistration = it.endRegistration,
                rateHikePrice = it.rateHikePrice,
                author = it.author.toModel()
            )
        )
    }
    return newList
}

fun ProductDTO.toModel(): ProductModel {
    return ProductModel(
        id = id,
        city = city?.toModel(),
        photos = photos?.toModel(),
        title = title,
        description = description,
        startDate = startDate,
        endDate = endDate,
        price = price,
        category = category,
        startRegistration = startRegistration,
        endRegistration = endRegistration,
        rateHikePrice = rateHikePrice,
        author = author.toModel()
    )
}
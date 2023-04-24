package com.example.auctionapp.data.model

import com.example.auctionapp.domain.models.ProductModel
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ProductDTO(
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
    val category: String?
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
                category = it.category
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
        category = category
    )
}
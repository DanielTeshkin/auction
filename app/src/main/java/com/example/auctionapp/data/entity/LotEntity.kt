package com.example.auctionapp.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.auctionapp.data.model.CitiesDTO
import com.example.auctionapp.data.model.toModel
import com.example.auctionapp.domain.models.AuthorModel
import com.example.auctionapp.domain.models.CitiesModel
import com.example.auctionapp.domain.models.PhotosModel
import com.example.auctionapp.domain.models.ProductModel

@Entity(tableName = LotContract.TABLE_NAME)
@TypeConverters(
    PhotoTypeConverter::class
)
data class LotEntity(
    @PrimaryKey
    @ColumnInfo(name = LotContract.Column.ID)
    val id: String,
    @ColumnInfo(name = LotContract.Column.START_DATE)
    val startDate: String,
    @ColumnInfo(name = LotContract.Column.END_DATE)
    val endDate: String,
    @ColumnInfo(name = LotContract.Column.DESCRIPTION)
    val description: String?,
    @ColumnInfo(name = LotContract.Column.CATEGORY)
    val category: String?,
    @ColumnInfo(name = LotContract.Column.TITLE)
    val title: String,
    @ColumnInfo(name = LotContract.Column.PRICE)
    val price: Long,
    @ColumnInfo(name = LotContract.Column.CITY)
    val city: CitiesDTO?,
    @ColumnInfo(name = LotContract.Column.IMAGE)
    val image: List<Photo>,
)

fun List<LotEntity>.toModel(): List<ProductModel> {
    val newList = arrayListOf<ProductModel>()
    this.forEach {
        newList.add(
            ProductModel(
                id = it.id,
                startDate = it.startDate,
                endDate = it.endDate,
                description = it.description,
                category = it.category,
                title = it.title,
                price = it.price,
                photos = it.image.toPhotosModel(),
                city = it.city?.toModel(),
                startRegistration = "",
                endRegistration = "",
                rateHikePrice = "",
                author = AuthorModel("", null,null,null,null,)
            )
        )
    }

    return newList
}

fun CitiesModel.toDTO(): CitiesDTO {
    return CitiesDTO(id, name)
}

fun List<ProductModel>.toEntity(): List<LotEntity> {
    val newList = arrayListOf<LotEntity>()
    this.forEach {
        newList.add(
            LotEntity(
                id = it.id,
                startDate = it.startDate!!,
                endDate = it.endDate!!,
                description = it.description,
                category = it.category,
                title = it.title!!,
                price = it.price,
                image = it.photos?.toPhoto()!!,
                city = it.city?.toDTO()
            )
        )
    }
    return newList
}

fun LotEntity.toModel(): ProductModel {
    return ProductModel(
        id = id,
        startDate = startDate,
        endDate = endDate,
        description = description,
        category = category,
        title = title,
        price = price,
        photos = image.toPhotosModel(),
        city = city?.toModel(),
        startRegistration = "",
        endRegistration = "",
        rateHikePrice = "",
        author = AuthorModel("", null,null,null,null,)
    )
}

fun ProductModel.toEntity(): LotEntity {
    return LotEntity(
        id = id,
        startDate = startDate!!,
        endDate = endDate!!,
        description = description,
        category = category,
        title = title!!,
        price = price,
        image = photos?.toPhoto()!!,
        city = city?.toDTO()
    )
}

fun List<PhotosModel>.toPhoto(): List<Photo> {
    val newList = arrayListOf<Photo>()
    this.forEach {
        newList.add(
            Photo(
                id = it.id,
                file = it.file
            )
        )
    }

    return newList
}

fun List<Photo>.toPhotosModel(): List<PhotosModel> {
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




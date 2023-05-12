package com.example.auctionapp.domain.repository

import com.example.auctionapp.domain.models.BaseResponse
import com.example.auctionapp.domain.models.ElectedProductModel
import com.example.auctionapp.domain.models.ProductModel

interface ProductRepository {

    suspend fun getProducts(
        q: String, sort: String,
        price_min: String,
        price_max: String,
        city: String
    ): BaseResponse<List<ProductModel>>

    suspend fun insertInFavorite(item: ProductModel): BaseResponse<Unit>

    suspend fun getAllFavorite(): BaseResponse<List<ElectedProductModel>>

    suspend fun deleteProduct(info: ProductModel): BaseResponse<Unit>


}
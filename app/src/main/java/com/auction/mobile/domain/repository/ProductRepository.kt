package com.auction.mobile.domain.repository

import com.auction.mobile.domain.models.BaseResponse
import com.auction.mobile.domain.models.ElectedProductModel
import com.auction.mobile.domain.models.MyBidModel
import com.auction.mobile.domain.models.ProductModel

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

    suspend fun getBidList(): BaseResponse<List<MyBidModel>>


}
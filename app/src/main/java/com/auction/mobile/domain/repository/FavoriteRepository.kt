package com.auction.mobile.domain.repository

import com.auction.mobile.domain.models.BaseResponse
import com.auction.mobile.domain.models.ElectedProductModel
import com.auction.mobile.domain.models.ProductModel

interface FavoriteRepository {

    suspend fun getAllFavorite(): BaseResponse<List<ElectedProductModel>>

    suspend fun deleteProduct(info: ProductModel)

}
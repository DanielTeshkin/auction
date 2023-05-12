package com.example.auctionapp.domain.repository

import com.example.auctionapp.domain.models.BaseResponse
import com.example.auctionapp.domain.models.ElectedProductModel
import com.example.auctionapp.domain.models.ProductModel

interface FavoriteRepository {

    suspend fun getAllFavorite(): BaseResponse<List<ElectedProductModel>>

    suspend fun deleteProduct(info: ProductModel)

}
package com.example.auctionapp.domain.repository

import com.example.auctionapp.domain.models.ProductModel

interface FavoriteRepository {

    suspend fun getAllFavorite(): List<ProductModel>



    suspend fun deleteProduct(info: ProductModel)

}
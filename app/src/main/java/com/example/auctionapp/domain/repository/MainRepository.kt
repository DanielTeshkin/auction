package com.example.auctionapp.domain.repository

import com.example.auctionapp.domain.models.ProductModel

interface MainRepository {

    suspend fun getAllFavorite(): List<ProductModel>

}
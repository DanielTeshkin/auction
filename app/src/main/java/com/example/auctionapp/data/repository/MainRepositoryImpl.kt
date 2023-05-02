package com.example.auctionapp.data.repository

import com.example.auctionapp.data.database.LotDao
import com.example.auctionapp.data.entity.toModel
import com.example.auctionapp.domain.models.ProductModel
import com.example.auctionapp.domain.repository.MainRepository
import javax.inject.Inject


class MainRepositoryImpl @Inject constructor(
    private val db: LotDao
): MainRepository {
    override suspend fun getAllFavorite(): List<ProductModel> {
        return db.getAllFavorites().toModel()
    }
}
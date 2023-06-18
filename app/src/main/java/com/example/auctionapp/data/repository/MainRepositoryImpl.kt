package com.example.auctionapp.data.repository

import com.example.auctionapp.data.database.LotDao
import com.example.auctionapp.data.entity.toModel
import com.example.auctionapp.data.model.toModel
import com.example.auctionapp.data.networking.ApiService
import com.example.auctionapp.domain.models.ElectedProductModel
import com.example.auctionapp.domain.models.ProductModel
import com.example.auctionapp.domain.repository.MainRepository
import javax.inject.Inject


class MainRepositoryImpl @Inject constructor(
    private val db: LotDao,
    private val api: ApiService
): MainRepository {
    override suspend fun getAllFavorite(): List<ElectedProductModel> {
        return try {
            api.getFavoriteProducts().results.map { it.toModel() }
        } catch (e: java.lang.Exception) {
            emptyList()
        }
    }
}
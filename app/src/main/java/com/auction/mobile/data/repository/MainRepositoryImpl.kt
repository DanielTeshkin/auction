package com.auction.mobile.data.repository

import com.auction.mobile.data.database.LotDao
import com.auction.mobile.data.model.toModel
import com.auction.mobile.data.networking.ApiService
import com.auction.mobile.domain.models.ElectedProductModel
import com.auction.mobile.domain.repository.MainRepository
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
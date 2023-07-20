package com.auction.mobile.data.repository

import android.util.Log
import com.auction.mobile.data.database.LotDao
import com.auction.mobile.data.model.toModel
import com.auction.mobile.data.networking.ApiService
import com.auction.mobile.domain.models.BaseResponse
import com.auction.mobile.domain.models.ElectedProductModel
import com.auction.mobile.domain.models.ProductModel
import com.auction.mobile.domain.repository.FavoriteRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class FavoriteRepositoryImpl @Inject constructor(
    private val api: ApiService,
    private val db: LotDao
) : FavoriteRepository {

    override suspend fun getAllFavorite(): BaseResponse<List<ElectedProductModel>> {
        return withContext(Dispatchers.IO) {
            try {
                val result = api.getFavoriteProducts()
                BaseResponse.Success(result.results.map { it.toModel() })
            } catch (e: java.lang.Exception) {
                BaseResponse.Error(e.message.toString())
            }
        }
    }


    override suspend fun deleteProduct(info: ProductModel) {
        withContext(Dispatchers.IO) {
            try {
                api.deleteElectedItem(info.id)

            } catch (e: Exception) {
                Log.d("TTT", e.message.toString())
            }
        }
    }
}
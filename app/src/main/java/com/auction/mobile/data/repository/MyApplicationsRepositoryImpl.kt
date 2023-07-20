package com.auction.mobile.data.repository

import android.util.Log
import com.auction.mobile.data.model.toModel
import com.auction.mobile.data.networking.ApiService
import com.auction.mobile.domain.models.BaseResponse
import com.auction.mobile.domain.models.MyBidModel
import com.auction.mobile.domain.repository.MyApplicationsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MyApplicationsRepositoryImpl @Inject constructor(
    private val api: ApiService
): MyApplicationsRepository {

    override suspend fun getBidProducts(): BaseResponse<List<MyBidModel>> {
        return withContext(Dispatchers.IO) {
            try {
                val res = api.getMyBidsList()
                BaseResponse.Success(res.results.map { it.toModel() })
            } catch (e: java.lang.Exception) {
                Log.d("TTT", e.message.toString())
                BaseResponse.Error(e.message.toString())
            }
        }
    }
}
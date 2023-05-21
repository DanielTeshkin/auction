package com.example.auctionapp.data.repository

import android.util.Log
import com.example.auctionapp.data.model.toModel
import com.example.auctionapp.data.networking.ApiService
import com.example.auctionapp.domain.models.BaseResponse
import com.example.auctionapp.domain.models.MyBidModel
import com.example.auctionapp.domain.repository.MyApplicationsRepository
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
package com.example.auctionapp.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.auctionapp.data.model.NewPriceDTO
import com.example.auctionapp.data.model.toModel
import com.example.auctionapp.data.networking.ApiService
import com.example.auctionapp.domain.models.BaseResponse
import com.example.auctionapp.domain.models.ProductModel
import com.example.auctionapp.domain.repository.DetailInfoRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class DetailInfoRepositoryImpl @Inject constructor(
    private val api: ApiService
): DetailInfoRepository {

    override suspend fun getDetailInfo(id: String): BaseResponse<ProductModel> {
        return withContext(Dispatchers.IO) {
            try {
                val result = api.getProductById(id)
                BaseResponse.Success(result.toModel())
            } catch(e: Exception) {
                BaseResponse.Error(e.message.toString())
            }
        }
    }

    override suspend fun raisePrice(id: String, price: String): BaseResponse<String> {
        return withContext(Dispatchers.IO) {
            try {
                val result = api.raisePrice(id, NewPriceDTO(price))
                BaseResponse.Success(result)
            } catch (e: Exception) {
                Log.d("TTT", e.message.toString())
                BaseResponse.Error(e.message.toString())
            }
        }

    }
}
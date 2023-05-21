package com.example.auctionapp.data.repository

import android.util.Log
import com.example.auctionapp.data.database.LotDao
import com.example.auctionapp.data.entity.toEntity
import com.example.auctionapp.data.entity.toModel
import com.example.auctionapp.data.model.NewPriceDTO
import com.example.auctionapp.data.model.toDTO
import com.example.auctionapp.data.model.toModel
import com.example.auctionapp.data.networking.ApiService
import com.example.auctionapp.domain.models.BaseResponse
import com.example.auctionapp.domain.models.BidCreateRequestModel
import com.example.auctionapp.domain.models.ProductModel
import com.example.auctionapp.domain.models.RacePriceModel
import com.example.auctionapp.domain.repository.DetailInfoRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class DetailInfoRepositoryImpl @Inject constructor(
    private val api: ApiService,
    private val db: LotDao
) : DetailInfoRepository {

    override suspend fun getDetailInfo(id: String): BaseResponse<ProductModel> {
        return withContext(Dispatchers.IO) {
            try {
                val result = api.getProductById(id)
                BaseResponse.Success(result.toModel())
            } catch (e: Exception) {
                BaseResponse.Error(e.message.toString())
            }
        }
    }

//    override suspend fun raisePrice(id: String, price: String): BaseResponse<String> {
//        return withContext(Dispatchers.IO) {
//            try {
//                val result = api.raisePrice(id, NewPriceDTO(price))
//                BaseResponse.Success(result)
//            } catch (e: Exception) {
//                Log.d("TTT", e.message.toString())
//                BaseResponse.Error(e.message.toString())
//            }
//        }
//
//    }

    override suspend fun getAllFavorite(): List<ProductModel> {
        return db.getAllFavorites().toModel()
    }

    override suspend fun insertInFavorite(item: ProductModel) {
        withContext(Dispatchers.IO) {
            try {
                db.insertProduct(item.toEntity())
            } catch (e: java.lang.Exception) {
                Log.d("TTT", e.message.toString())
            }
        }
    }

    override suspend fun deleteProduct(info: ProductModel) {
        withContext(Dispatchers.IO) {
            try {
                db.deleteProduct(info.toEntity())
            } catch (e: Exception) {
                Log.d("TTT", e.message.toString())
            }
        }
    }

    override suspend fun racePrice(info: RacePriceModel, id: String): BaseResponse<String> {
        return withContext(Dispatchers.IO) {
            try {
                api.racePrice(id = id, info = info.toDTO())
                BaseResponse.Success("Успешно")
            } catch (e:java.lang.Exception) {
                BaseResponse.Error(e.message.toString())
            }
        }
    }

    override suspend fun createBid(info: BidCreateRequestModel): BaseResponse<String> {
        return withContext(Dispatchers.IO) {
            try {
                api.createBid(request = info.toDTO())
                BaseResponse.Success("Успешно")
            } catch (e:java.lang.Exception) {
                BaseResponse.Error(e.message.toString())
            }
        }
    }
}
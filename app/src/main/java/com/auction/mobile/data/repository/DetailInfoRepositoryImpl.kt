package com.auction.mobile.data.repository

import android.util.Log
import com.auction.mobile.data.database.LotDao
import com.auction.mobile.data.model.ProductToSetFavoriteDTO
import com.auction.mobile.data.model.toDTO
import com.auction.mobile.data.model.toModel
import com.auction.mobile.data.networking.ApiService
import com.auction.mobile.domain.models.*
import com.auction.mobile.domain.repository.DetailInfoRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class DetailInfoRepositoryImpl @Inject constructor(
    private val api: ApiService,
    private val db: LotDao
) : DetailInfoRepository {

    override suspend fun getBidList(): BaseResponse<List<MyBidModel>> {
        return withContext(Dispatchers.IO) {
            try {
                val res = api.getMyBidsList()
                BaseResponse.Success(res.results.map { it.toModel() })
            } catch (e: java.lang.Exception) {
                BaseResponse.Error(e.message.toString())
            }
        }
    }

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

    override suspend fun getAllFavorite(): List<ElectedProductModel> {
        return withContext(Dispatchers.IO) {
            try {
                api.getFavoriteProducts().results.map { it.toModel() }
            } catch (e: Exception) {
                emptyList()
            }
        }
    }

    override suspend fun insertInFavorite(item: ProductModel) {
        withContext(Dispatchers.IO) {
            try {
                api.addProductToFavorite(ProductToSetFavoriteDTO(item.id))
            } catch (e: java.lang.Exception) {
                Log.d("TTT", e.message.toString())
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

    override suspend fun racePrice(info: RacePriceModel, id: String): BaseResponse<String> {
        return withContext(Dispatchers.IO) {
            try {
                api.racePrice(id = id, info = info.toDTO())
                BaseResponse.Success("Успешно")
            } catch (e: java.lang.Exception) {
                BaseResponse.Error(e.message.toString())
            }
        }
    }

    override suspend fun createBid(info: BidCreateRequestModel): BaseResponse<String> {
        return withContext(Dispatchers.IO) {
            try {
                api.createBid(request = info.toDTO())
                BaseResponse.Success("Успешно")
            } catch (e: java.lang.Exception) {
                BaseResponse.Error(e.message.toString())
            }
        }
    }
}
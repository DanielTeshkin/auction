package com.auction.mobile.domain.repository

import com.auction.mobile.data.model.toModel
import com.auction.mobile.domain.models.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.lang.Exception

interface DetailInfoRepository {
    suspend fun getDetailInfo(id: String): BaseResponse<ProductModel>
//    suspend fun raisePrice(id: String, price: String): BaseResponse<String>
    suspend fun getAllFavorite(): List<ElectedProductModel>
    suspend fun getBidList(): BaseResponse<List<MyBidModel>>
    suspend fun insertInFavorite(item: ProductModel)
    suspend fun deleteProduct(info: ProductModel)
    suspend fun racePrice(info: RacePriceModel, id: String): BaseResponse<String>
    suspend fun createBid(info: BidCreateRequestModel): BaseResponse<String>
}
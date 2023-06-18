package com.example.auctionapp.domain.repository

import com.example.auctionapp.data.model.RacePriceDTO
import com.example.auctionapp.domain.models.*

interface DetailInfoRepository {
    suspend fun getDetailInfo(id: String): BaseResponse<ProductModel>
//    suspend fun raisePrice(id: String, price: String): BaseResponse<String>
    suspend fun getAllFavorite(): List<ElectedProductModel>
    suspend fun insertInFavorite(item: ProductModel)
    suspend fun deleteProduct(info: ProductModel)
    suspend fun racePrice(info: RacePriceModel, id: String): BaseResponse<String>
    suspend fun createBid(info: BidCreateRequestModel): BaseResponse<String>
}
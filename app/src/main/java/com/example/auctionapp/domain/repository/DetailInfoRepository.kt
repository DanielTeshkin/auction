package com.example.auctionapp.domain.repository

import com.example.auctionapp.data.model.RacePriceDTO
import com.example.auctionapp.domain.models.BaseResponse
import com.example.auctionapp.domain.models.BidCreateRequestModel
import com.example.auctionapp.domain.models.ProductModel
import com.example.auctionapp.domain.models.RacePriceModel

interface DetailInfoRepository {
    suspend fun getDetailInfo(id: String): BaseResponse<ProductModel>
//    suspend fun raisePrice(id: String, price: String): BaseResponse<String>
    suspend fun getAllFavorite(): List<ProductModel>
    suspend fun insertInFavorite(item: ProductModel)
    suspend fun deleteProduct(info: ProductModel)
    suspend fun racePrice(info: RacePriceModel, id: String): BaseResponse<String>
    suspend fun createBid(info: BidCreateRequestModel): BaseResponse<String>
}
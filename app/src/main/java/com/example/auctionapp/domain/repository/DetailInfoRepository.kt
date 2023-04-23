package com.example.auctionapp.domain.repository

import com.example.auctionapp.domain.models.BaseResponse
import com.example.auctionapp.domain.models.ProductModel

interface DetailInfoRepository {

    suspend fun getDetailInfo(id: String): BaseResponse<ProductModel>
}
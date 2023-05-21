package com.example.auctionapp.domain.repository

import com.example.auctionapp.domain.models.BaseResponse
import com.example.auctionapp.domain.models.MyBidModel
import javax.inject.Inject

interface MyApplicationsRepository {

    suspend fun getBidProducts(): BaseResponse<List<MyBidModel>>
}
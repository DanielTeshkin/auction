package com.auction.mobile.domain.repository

import com.auction.mobile.domain.models.BaseResponse
import com.auction.mobile.domain.models.MyBidModel

interface MyApplicationsRepository {

    suspend fun getBidProducts(): BaseResponse<List<MyBidModel>>
}
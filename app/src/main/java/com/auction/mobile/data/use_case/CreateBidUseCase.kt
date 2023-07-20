package com.auction.mobile.data.use_case

import com.auction.mobile.domain.models.BaseResponse
import com.auction.mobile.domain.models.BidCreateRequestModel
import com.auction.mobile.domain.repository.DetailInfoRepository
import javax.inject.Inject

class CreateBidUseCase @Inject constructor(
    private val repo: DetailInfoRepository
) {
    suspend fun execute(info: BidCreateRequestModel): BaseResponse<String> {
        return repo.createBid(info)
    }
}
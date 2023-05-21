package com.example.auctionapp.data.use_case

import com.example.auctionapp.domain.models.BaseResponse
import com.example.auctionapp.domain.models.BidCreateRequestModel
import com.example.auctionapp.domain.repository.DetailInfoRepository
import javax.inject.Inject

class CreateBidUseCase @Inject constructor(
    private val repo: DetailInfoRepository
) {
    suspend fun execute(info: BidCreateRequestModel): BaseResponse<String> {
        return repo.createBid(info)
    }
}
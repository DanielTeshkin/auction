package com.example.auctionapp.domain.repository

import com.example.auctionapp.domain.models.BaseResponse
import com.example.auctionapp.domain.models.GetUserInfoModel

interface ProfileRepository {

    suspend fun getProfileInfo(): BaseResponse<GetUserInfoModel>
}
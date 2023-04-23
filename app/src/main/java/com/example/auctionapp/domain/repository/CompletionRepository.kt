package com.example.auctionapp.domain.repository

import com.example.auctionapp.domain.models.BaseResponse
import com.example.auctionapp.domain.models.CitiesModel
import com.example.auctionapp.domain.models.UserInfoResponseModel

interface CompletionRepository {

    suspend fun postCompletion(
        firstName: String?,
        lastName: String?,
        patronymic: String?,
        cityPk: String,
        email: String
    ): BaseResponse<UserInfoResponseModel>

    suspend fun getCities(): BaseResponse<List<CitiesModel>>
}
package com.auction.mobile.domain.repository

import com.auction.mobile.domain.models.BaseResponse
import com.auction.mobile.domain.models.CitiesModel
import com.auction.mobile.domain.models.UserInfoResponseModel

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
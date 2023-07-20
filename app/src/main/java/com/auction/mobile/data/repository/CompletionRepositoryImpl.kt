package com.auction.mobile.data.repository

import com.auction.mobile.data.model.UserInfoDTO
import com.auction.mobile.data.model.toModel
import com.auction.mobile.data.networking.ApiService
import com.auction.mobile.domain.models.BaseResponse
import com.auction.mobile.domain.models.CitiesModel
import com.auction.mobile.domain.models.UserInfoResponseModel
import com.auction.mobile.domain.repository.CompletionRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class CompletionRepositoryImpl @Inject constructor(
    val api: ApiService
): CompletionRepository {
    override suspend fun postCompletion(
        firstName: String?,
        lastName: String?,
        patronymic: String?,
        cityPk: String,
        email: String
    ): BaseResponse<UserInfoResponseModel> {
        return withContext(Dispatchers.IO) {
            try {
                val result = api.userInfo(UserInfoDTO(
                    firstName, lastName, patronymic, cityPk, email)
                ).toModel()
                BaseResponse.Success(result)
            } catch (e: java.lang.Exception) {
                BaseResponse.Error(e.message.toString())
            }
        }
    }

    override suspend fun getCities(): BaseResponse<List<CitiesModel>> {
        return withContext(Dispatchers.IO) {
            try {
                val result = api.getCitiesList().toModel()
                BaseResponse.Success(result)
            } catch (e: java.lang.Exception) {
                BaseResponse.Error(e.message.toString())
            }
        }
    }
}
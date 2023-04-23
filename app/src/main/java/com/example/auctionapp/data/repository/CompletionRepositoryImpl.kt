package com.example.auctionapp.data.repository

import com.example.auctionapp.data.model.UserInfoDTO
import com.example.auctionapp.data.model.toModel
import com.example.auctionapp.data.networking.ApiService
import com.example.auctionapp.domain.models.BaseResponse
import com.example.auctionapp.domain.models.CitiesModel
import com.example.auctionapp.domain.models.UserInfoResponseModel
import com.example.auctionapp.domain.repository.CompletionRepository
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
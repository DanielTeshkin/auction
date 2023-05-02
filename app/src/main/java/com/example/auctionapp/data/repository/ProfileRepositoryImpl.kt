package com.example.auctionapp.data.repository

import com.example.auctionapp.data.model.toModel
import com.example.auctionapp.data.networking.ApiService
import com.example.auctionapp.domain.models.BaseResponse
import com.example.auctionapp.domain.models.GetUserInfoModel
import com.example.auctionapp.domain.repository.ProfileRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ProfileRepositoryImpl @Inject constructor(
    private val api: ApiService
): ProfileRepository {

    override suspend fun getProfileInfo(): BaseResponse<GetUserInfoModel> {
    return withContext(Dispatchers.IO) {
        try {
            val result = api.getUserInfo()
            BaseResponse.Success(result.toModel())
        }  catch (e: java.lang.Exception) {
            BaseResponse.Error(e.message.toString())
        }
    }
    }
}
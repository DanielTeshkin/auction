package com.auction.mobile.data.repository

import com.auction.mobile.data.model.toModel
import com.auction.mobile.data.networking.ApiService
import com.auction.mobile.domain.models.BaseResponse
import com.auction.mobile.domain.models.GetUserInfoModel
import com.auction.mobile.domain.repository.ProfileRepository
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
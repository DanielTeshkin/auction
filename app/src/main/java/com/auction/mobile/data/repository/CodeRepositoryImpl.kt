package com.auction.mobile.data.repository

import com.auction.mobile.data.model.SendCodeDTO
import com.auction.mobile.data.networking.ApiService
import com.auction.mobile.domain.models.BaseResponse
import com.auction.mobile.domain.repository.CodeRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class CodeRepositoryImpl @Inject constructor(
    private val api: ApiService
): CodeRepository {
    override suspend fun sendCode(phone: String, code: String): BaseResponse<String> {
        return withContext(Dispatchers.IO) {
            try {
                val result = api.checkCode(SendCodeDTO(phone, code))
                BaseResponse.Success(result)
            } catch (e: java.lang.Exception) {
                BaseResponse.Error(e.message.toString())
            }
        }
    }

    override suspend fun sendCodeForPassRecovery(
        phone: String,
        code: String
    ): BaseResponse<String> {
        return withContext(Dispatchers.IO) {
            try {
                api.checkCodePassRecovery(SendCodeDTO(phone, code))
                BaseResponse.Success("Success")
            } catch (e: java.lang.Exception) {
                BaseResponse.Error(e.message.toString())
            }
        }

    }
}
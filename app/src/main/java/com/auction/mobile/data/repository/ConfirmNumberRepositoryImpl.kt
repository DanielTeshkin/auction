package com.auction.mobile.data.repository

import com.auction.mobile.data.model.ConfirmPhoneDTO
import com.auction.mobile.data.networking.ApiService
import com.auction.mobile.domain.models.BaseResponse
import com.auction.mobile.domain.repository.ConfirmNumberRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ConfirmNumberRepositoryImpl @Inject constructor(
    private val api: ApiService
) : ConfirmNumberRepository {
    override suspend fun sendNumber(info: String): BaseResponse<Any> {
        return withContext(Dispatchers.IO) {
            try {
                api.confirmCode(ConfirmPhoneDTO(info))
                BaseResponse.Success(Unit)
            } catch (e: Exception) {
                BaseResponse.Error(e.message.toString())
            }
        }
    }

    override suspend fun confirmPass(info: String): BaseResponse<Any> {
        return withContext(Dispatchers.IO) {
            try {
                api.confirmPass(ConfirmPhoneDTO(info))
                BaseResponse.Success("Успешно")
            } catch (e: java.lang.Exception) {
                BaseResponse.Error(e.message.toString())

            }

        }
    }
}


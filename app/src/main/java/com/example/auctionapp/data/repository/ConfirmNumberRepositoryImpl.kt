package com.example.auctionapp.data.repository

import com.example.auctionapp.data.model.ConfirmPhoneDTO
import com.example.auctionapp.data.model.toDTO
import com.example.auctionapp.data.model.toModel
import com.example.auctionapp.data.networking.ApiService
import com.example.auctionapp.domain.models.BaseResponse
import com.example.auctionapp.domain.models.ConfirmCodeResponse
import com.example.auctionapp.domain.models.ConfirmPhoneModel
import com.example.auctionapp.domain.repository.ConfirmNumberRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ConfirmNumberRepositoryImpl @Inject constructor(
    private val api: ApiService
) : ConfirmNumberRepository {
    override suspend fun sendNumber(info: String): BaseResponse<Any> {
        return withContext(Dispatchers.IO) {
            try {
                val result = api.confirmCode(ConfirmPhoneDTO(info)).toModel()
                BaseResponse.Success(result)
            } catch (e: Exception) {
                BaseResponse.Error(e.message.toString())
            }
        }
    }

    override suspend fun confirmPass(info: String): BaseResponse<Any> {
        return withContext(Dispatchers.IO) {
            try {
                api.confirmPass(ConfirmPhoneDTO(info))
                BaseResponse.Success<String>("Успешно")
            } catch (e: java.lang.Exception) {
                BaseResponse.Error(e.message.toString())

            }

        }
    }
}

}
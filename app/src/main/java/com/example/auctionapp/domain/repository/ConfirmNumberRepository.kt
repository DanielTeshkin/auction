package com.example.auctionapp.domain.repository

import android.telephony.mbms.StreamingServiceInfo
import com.example.auctionapp.domain.models.BaseResponse
import com.example.auctionapp.domain.models.ConfirmCodeResponse
import com.example.auctionapp.domain.models.ConfirmPhoneModel

interface ConfirmNumberRepository {

    suspend fun sendNumber(info: String): BaseResponse<Any>

    suspend fun confirmPass(info: String): BaseResponse<Any>
}
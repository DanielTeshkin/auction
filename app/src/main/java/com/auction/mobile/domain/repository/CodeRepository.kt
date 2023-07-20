package com.auction.mobile.domain.repository

import com.auction.mobile.domain.models.BaseResponse

interface CodeRepository {

    suspend fun sendCode(phone: String, code: String): BaseResponse<String>

    suspend fun sendCodeForPassRecovery(phone: String, code: String): BaseResponse<String>
}
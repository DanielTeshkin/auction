package com.auction.mobile.domain.repository

import com.auction.mobile.domain.models.BaseResponse

interface ConfirmNumberRepository {

    suspend fun sendNumber(info: String): BaseResponse<Any>

    suspend fun confirmPass(info: String): BaseResponse<Any>
}
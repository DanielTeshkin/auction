package com.auction.mobile.domain.repository

import com.auction.mobile.domain.models.BaseResponse

interface RecoveryPasswordRepository {
    suspend fun postNewPassword(
        userName: String,
        password1: String,
        password2: String
    ): BaseResponse<Unit>
}
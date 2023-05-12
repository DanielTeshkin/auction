package com.example.auctionapp.domain.repository

import com.example.auctionapp.domain.models.BaseResponse
import com.squareup.moshi.Json

interface RecoveryPasswordRepository {
    suspend fun postNewPassword(
        userName: String,
        password1: String,
        password2: String
    ): BaseResponse<Unit>
}
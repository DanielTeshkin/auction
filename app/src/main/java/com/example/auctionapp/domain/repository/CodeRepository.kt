package com.example.auctionapp.domain.repository

import com.example.auctionapp.domain.models.BaseResponse

interface CodeRepository {

    suspend fun sendCode(phone: String, code: String): BaseResponse<String>
}
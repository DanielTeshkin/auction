package com.example.auctionapp.domain.repository

import com.example.auctionapp.domain.models.BaseResponse
import com.example.auctionapp.domain.models.SignUpModel

interface LoginRepository {

    suspend fun login(logo: String, pass: String, isItRegister: Boolean): BaseResponse<SignUpModel>
}
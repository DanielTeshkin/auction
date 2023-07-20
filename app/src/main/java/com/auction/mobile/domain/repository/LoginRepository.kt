package com.auction.mobile.domain.repository

import com.auction.mobile.domain.models.BaseResponse
import com.auction.mobile.domain.models.SignUpModel

interface LoginRepository {

    suspend fun login(logo: String, pass: String, isItRegister: Boolean): BaseResponse<SignUpModel>
}
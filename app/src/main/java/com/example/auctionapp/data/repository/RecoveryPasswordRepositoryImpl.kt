package com.example.auctionapp.data.repository

import android.util.Log
import com.example.auctionapp.data.model.ResetPassDTO
import com.example.auctionapp.data.networking.ApiService
import com.example.auctionapp.domain.models.BaseResponse
import com.example.auctionapp.domain.repository.RecoveryPasswordRepository
import com.example.auctionapp.tools.PreferencesHelper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RecoveryPasswordRepositoryImpl @Inject constructor(
    private val api: ApiService,
    private val prefs: PreferencesHelper
): RecoveryPasswordRepository {
    override suspend fun postNewPassword(
        userName: String,
        password1: String,
        password2: String
    ): BaseResponse<Unit> {
        return withContext(Dispatchers.IO) {
            try {
                api.resetPassword(ResetPassDTO(userName, password1, password2))
//                prefs.mAccessToken = res.accessToken
//                prefs.mRefreshToken = res.refreshToken
                BaseResponse.Success(Unit)
            } catch (e: java.lang.Exception) {
                BaseResponse.Error(e.message.toString())
            }
        }

    }
}
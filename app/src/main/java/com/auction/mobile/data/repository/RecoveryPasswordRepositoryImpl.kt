package com.auction.mobile.data.repository

import com.auction.mobile.data.model.ResetPassDTO
import com.auction.mobile.data.networking.ApiService
import com.auction.mobile.domain.models.BaseResponse
import com.auction.mobile.domain.repository.RecoveryPasswordRepository
import com.auction.mobile.tools.PreferencesHelper
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
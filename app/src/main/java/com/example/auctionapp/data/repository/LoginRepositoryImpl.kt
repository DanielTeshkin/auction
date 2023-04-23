package com.example.auctionapp.data.repository

import com.example.auctionapp.data.model.SignInDTO
import com.example.auctionapp.data.model.toSignUpModel
import com.example.auctionapp.data.networking.ApiService
import com.example.auctionapp.domain.models.BaseResponse
import com.example.auctionapp.domain.models.SignUpModel
import com.example.auctionapp.domain.repository.LoginRepository
import com.example.auctionapp.tools.PreferencesHelper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject


class LoginRepositoryImpl @Inject constructor(
    private val api: ApiService,
    private val prefs: PreferencesHelper
): LoginRepository {
    override suspend fun login(logo: String, pass: String, isItRegister: Boolean): BaseResponse<SignUpModel> {
        return withContext(Dispatchers.IO) {
            try {
                val result = if (isItRegister) {
                     api.signUp(SignInDTO(
                        logo,
                        pass
                    )).toSignUpModel()
                } else {
                    api.signIn(SignInDTO(
                        logo,pass
                    )).toSignUpModel()
                }
                prefs.mAccessToken = result.accessToken
                prefs.mRefreshToken = result.refreshToken
                BaseResponse.Success(result)
            } catch (e: Exception) {
                BaseResponse.Error(e.message.toString())
            }
        }
    }


}
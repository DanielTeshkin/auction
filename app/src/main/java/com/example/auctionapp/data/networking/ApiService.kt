package com.example.auctionapp.data.networking

import com.example.auctionapp.data.*
import com.example.auctionapp.data.model.*
import kotlinx.coroutines.flow.Flow
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST

interface ApiService {

    @POST("confirm/phone/")
    suspend fun confirmCode(
        @Body confirmCodeModel: ConfirmPhoneDTO
    ): ConfirmPhoneResponseDTO

    @POST("confirm/phone/confirmed/")
    suspend fun checkCode(
        @Body info: SendCodeDTO
    ): String

    @POST("sign-in/")
    suspend fun signIn(
        info: SignInDTO
    ): SignUpResponseDTO

    @POST("sign-up/")
    suspend fun signUp(
        info: SignInDTO
    ): SignUpResponseDTO

    @GET("cities/")
    suspend fun getCitiesList(): CitiesDTO

    @POST("token/refresh/")
    suspend fun getRefreshToken(
        info: GetRefreshDTO
    ): GetRefreshResponseDTO

    @PATCH("user_info/")
    suspend fun userInfo(
        info: UserInfoDTO
    ): UserInfoDTO

}
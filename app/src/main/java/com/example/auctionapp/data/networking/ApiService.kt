package com.example.auctionapp.data.networking

import com.example.auctionapp.data.*
import com.example.auctionapp.data.model.*
import com.example.auctionapp.domain.models.BaseResponse
import kotlinx.coroutines.flow.Flow
import okhttp3.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path

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
        @Body info: SignInDTO
    ): SignUpResponseDTO

    @POST("sign-up/")
    suspend fun signUp(
        @Body info: SignInDTO
    ): SignUpResponseDTO

    @GET("cities/")
    suspend fun getCitiesList(): List<CitiesDTO>

    @POST("token/refresh/")
    fun getRefreshToken(
        @Body info: GetRefreshDTO
    ): retrofit2.Response<GetRefreshResponseDTO>

    @PATCH("user_info/")
    suspend fun userInfo(
        @Body info: UserInfoDTO
    ): UserInfoResponseDTO

    @GET("product/")
    suspend fun getProduct(): ProductBaseResponseDTO

    @GET("product/{id}")
    suspend fun getProductById(
        @Path("id") id: String
    ): ProductDTO

}
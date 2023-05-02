package com.example.auctionapp.data.networking

import com.example.auctionapp.data.*
import com.example.auctionapp.data.model.*
import com.example.auctionapp.domain.models.BaseResponse
import kotlinx.coroutines.flow.Flow
import okhttp3.Response
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

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


    @PATCH("user_info/")
    suspend fun userInfo(
        @Body info: UserInfoDTO
    ): UserInfoResponseDTO

    @GET("user_info/")
    suspend fun getUserInfo(): GetUserInfoDTO

    @GET("product/")
    suspend fun getProduct(
        @Query("q") text: String,
        @Query("sort") sort: String
    ): ProductBaseResponseDTO

    @GET("product/{id}")
    suspend fun getProductById(
        @Path("id") id: String
    ): ProductDTO

    @POST("product/{id}/raise_price/")
    suspend fun raisePrice(
        @Path("id") id: String,
        @Body price: NewPriceDTO
    ): String

}
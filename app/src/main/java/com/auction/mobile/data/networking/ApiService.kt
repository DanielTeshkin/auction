package com.auction.mobile.data.networking

import com.auction.mobile.data.*
import com.auction.mobile.data.model.*
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @POST("confirm/phone/")
    suspend fun confirmCode(
        @Body confirmCodeModel: ConfirmPhoneDTO
    )

    @POST("confirm/pass/")
    suspend fun confirmPass(
        @Body model: ConfirmPhoneDTO
    )

    @POST("product/elected/")
    suspend fun addProductToFavorite(
        @Body item: ProductToSetFavoriteDTO
    )

    @DELETE("product/elected/{id}/")
    suspend fun deleteElectedItem(
        @Path("id") id: String
    )

    @GET("product/elected/")
    suspend fun getFavoriteProducts(): ProductBaseResponseDTO<FavoriteProductDTO>

    @GET("product/active/")
    suspend fun getActiveProductList(): ProductBaseResponseDTO<ProductDTO>

    @POST("confirm/phone/confirmed/")
    suspend fun checkCode(
        @Body info: SendCodeDTO
    ): String

    @POST("confirm/pass/confirmed/")
    suspend fun checkCodePassRecovery(
        @Body info: SendCodeDTO
    ): String

    @POST("reset_password/")
    suspend fun resetPassword(
        @Body info: ResetPassDTO
    )

    @POST("sign-in/")
    suspend fun signIn(
        @Body info: SignInDTO
    ): SignUpResponseDTO

    @POST("sign-up/")
    suspend fun signUp(
        @Body info: SignInDTO
    ): SignUpResponseDTO

    @POST("product/{id}/raise_price/")
    suspend fun racePrice(
        @Path(value = "id") id: String,
        @Body info: RacePriceDTO
    )

    @GET("bid/")
    suspend fun getMyBidsList(): ProductBaseResponseDTO<MyBidDTO>

    @POST("bid/create/")
    suspend fun createBid(@Body request: BidCreateRequestDTO)

    @GET("cities/")
    suspend fun getCitiesList(): List<CitiesDTO>


    @PATCH("user_info/")
    suspend fun userInfo(
        @Body info: UserInfoDTO
    ): UserInfoResponseDTO

    @GET("user_info/")
    suspend fun getUserInfo(): GetUserInfoDTO

    @GET("product/won/")
    suspend fun getWonProductList(): ProductBaseResponseDTO<ProductDTO>

    @GET("product/")
    suspend fun getProduct(
        @Query("q") text: String,
        @Query("sort") sort: String,
        @Query("price_min") price_min: String,
        @Query("price_max") price_max: String,
        @Query("city") city: String
    ): ProductBaseResponseDTO<ProductDTO>

    @GET("product/{id}")
    suspend fun getProductById(
        @Path("id") id: String
    ): ProductDTO

//    @POST("product/{id}/raise_price/")
//    suspend fun raisePrice(
//        @Path("id") id: String,
//        @Body price: NewPriceDTO
//    ): String

}
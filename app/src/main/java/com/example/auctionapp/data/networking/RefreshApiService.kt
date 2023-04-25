package com.example.auctionapp.data.networking

import com.example.auctionapp.data.model.GetRefreshDTO
import com.example.auctionapp.data.model.GetRefreshResponseDTO
import retrofit2.http.Body
import retrofit2.http.POST

interface RefreshApiService {

    @POST("token/refresh/")
    suspend fun getRefreshToken(
        @Body info: GetRefreshDTO
    ): retrofit2.Response<GetRefreshResponseDTO>
}
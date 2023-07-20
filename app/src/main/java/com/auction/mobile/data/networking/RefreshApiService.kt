package com.auction.mobile.data.networking

import com.auction.mobile.data.model.GetRefreshDTO
import com.auction.mobile.data.model.GetRefreshResponseDTO
import retrofit2.http.Body
import retrofit2.http.POST

interface RefreshApiService {

    @POST("token/refresh/")
    suspend fun getRefreshToken(
        @Body info: GetRefreshDTO
    ): retrofit2.Response<GetRefreshResponseDTO>
}
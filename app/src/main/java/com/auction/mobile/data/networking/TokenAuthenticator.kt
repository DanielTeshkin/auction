package com.auction.mobile.data.networking

import android.util.Log
import com.auction.mobile.data.model.GetRefreshDTO
import com.auction.mobile.tools.PreferencesHelper
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TokenAuthenticator @Inject constructor(private val apiService: RefreshApiService, private val prefs: PreferencesHelper) : Interceptor {
    private var tokenRefreshed = false

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        var response = chain.proceed(request)

        if (response.code == 401) {
            runBlocking {
                Log.d("REFRESH", "REFRESH START")
                if (!tokenRefreshed) {
                    tokenRefreshed = true
                    val refreshToken = prefs.mRefreshToken

                    val responseToken = apiService.getRefreshToken(GetRefreshDTO(refreshToken))

                    if (responseToken.isSuccessful) {
                        val accessToken = responseToken.body()?.accessToken
                        prefs.mAccessToken = accessToken!!
                        val newRequest = request.newBuilder()
                            .header("Authorization", "Bearer $accessToken")
                            .build()
                        response = chain.proceed(newRequest)
                    } else {
                        Log.e("TokenAuthenticator", "Failed to refresh token")
                    }
                } else {
                    delay(1000)
                    val newAccessToken = prefs.mAccessToken
                    val newRequest = request.newBuilder()
                        .header("Authorization", "Bearer $newAccessToken")
                        .build()
                    response = chain.proceed(newRequest)
                }
            }
        }

        return response
    }

}
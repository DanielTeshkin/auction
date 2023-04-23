package com.example.auctionapp.data.networking

import android.util.Log
import com.example.auctionapp.data.model.GetRefreshDTO
import com.example.auctionapp.tools.PreferencesHelper
import okhttp3.Interceptor
import okhttp3.Response
import java.net.HttpURLConnection
import javax.inject.Inject
import javax.inject.Singleton

//@Singleton
//class TokenAuthenticator @Inject constructor(private val apiService: ApiService, private val prefs: PreferencesHelper) : Interceptor {
//    private var tokenRefreshed = false
//
//    override fun intercept(chain: Interceptor.Chain): Response {
//        val request = chain.request()
//        var response = chain.proceed(request)
//
//        if (response.code == HttpURLConnection.HTTP_UNAUTHORIZED) {
//            synchronized(this) {
//                if (!tokenRefreshed) {
//                    tokenRefreshed = true
//                    val refreshToken = prefs.mRefreshToken
//
//                    val responseToken = apiService.getRefreshToken(GetRefreshDTO(refreshToken))
//
//                    if (responseToken.isSuccessful) {
//                        val accessToken = responseToken.body()?.accessToken
//                        prefs.mAccessToken = accessToken!!
//                        val newRequest = request.newBuilder()
//                            .header("Authorization", "Bearer $accessToken")
//                            .build()
//                        response = chain.proceed(newRequest)
//                    } else {
//                        Log.e("TokenAuthenticator", "Failed to refresh token")
//                    }
//                } else {
//                    Thread.sleep(1000)
//                    val newAccessToken = prefs.mAccessToken
//                    val newRequest = request.newBuilder()
//                        .header("Authorization", "Bearer $newAccessToken")
//                        .build()
//                    response = chain.proceed(newRequest)
//                }
//            }
//        }
//
//        return response
//    }
//
//}
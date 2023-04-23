package com.example.auctionapp.data.networking

import com.example.auctionapp.tools.PreferencesHelper
import okhttp3.Interceptor
import okhttp3.Response

class TokenInterceptor(private val helper: PreferencesHelper) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()
        val token = helper.mRefreshToken
        val url = original.newBuilder()
            .addHeader("Authorization", "token $token").build()
        return chain.proceed(url)
    }
}
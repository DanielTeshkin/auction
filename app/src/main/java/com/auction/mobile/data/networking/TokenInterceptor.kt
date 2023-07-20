package com.auction.mobile.data.networking

import com.auction.mobile.tools.PreferencesHelper
import okhttp3.Interceptor
import okhttp3.Response

class TokenInterceptor(private val helper: PreferencesHelper) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()
        val token = helper.mAccessToken
        return if (token != "") {
            val url = original.newBuilder()
                .addHeader("Authorization", token.withBearer()).build()
            chain.proceed(url)
        } else {
            chain.proceed(chain.request())
        }

    }

    private fun String.withBearer() = "Bearer $this"

}
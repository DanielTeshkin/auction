package com.example.auctionapp.data.networking

import android.util.Log
import okhttp3.Interceptor
import okhttp3.Response
import java.net.HttpURLConnection
import javax.inject.Inject
import javax.inject.Singleton

//@Singleton
//class TokenAuthenticator @Inject constructor(private val apiService: ApiService) : Interceptor {
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
//
//                    // Выполняем запрос на обновление access token с помощью refresh token
//                    val refreshToken = getRefreshToken() // получаем refresh token из SharedPreferences или другого места
//                    val responseToken = apiService.getRefreshToken(refreshToken).execute()
//
//                    if (responseToken.isSuccessful) {
//                        // Обновляем access token и добавляем его в заголовок авторизации
//                        val accessToken = responseToken.body()?.accessToken
//                        saveAccessToken(accessToken) // сохраняем access token в SharedPreferences или другом месте
//                        val newRequest = request.newBuilder()
//                            .header("Authorization", "Bearer $accessToken")
//                            .build()
//                        response = chain.proceed(newRequest)
//                    } else {
//                        // Ошибка обновления токена, логируем ее
//                        Log.e("TokenAuthenticator", "Failed to refresh token")
//                    }
//                } else {
//                    // Другой запрос уже обновляет токен, ждем его завершения и повторяем запрос
//                    Thread.sleep(1000) // задержка на случай, если запрос обновления токена еще не завершен
//                    val newAccessToken = getAccessToken() // получаем обновленный access token
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

    // Функции getRefreshToken, saveAccessToken и getAccessToken нужно реализовать в соответствии с вашей логикой
//}
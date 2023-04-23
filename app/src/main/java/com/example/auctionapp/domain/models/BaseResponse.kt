package com.example.auctionapp.domain.models


sealed class BaseResponse<out T : Any> {
    data class Success<out T : Any>(val data: T) : BaseResponse<T>()
    data class Error(val message: String) : BaseResponse<String>()
}

package com.auction.mobile.domain.models


sealed class BaseResponse<out T : Any> {
    data class Success<out T : Any>(val data: T) : BaseResponse<T>()
    data class Error<out T: Any>(val message: String) : BaseResponse<T>()
}

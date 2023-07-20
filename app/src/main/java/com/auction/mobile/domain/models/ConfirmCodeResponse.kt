package com.auction.mobile.domain.models

data class ConfirmCodeResponse(
val phone: String,
val isAuthorized: Boolean
)

package com.example.auctionapp.data.model

import android.text.SpannableString
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class GetRefreshDTO(
    @Json(name = "refresh")
    val refresh: SpannableString
)
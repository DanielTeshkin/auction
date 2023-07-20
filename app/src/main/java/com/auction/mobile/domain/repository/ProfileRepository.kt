package com.auction.mobile.domain.repository

import com.auction.mobile.domain.models.BaseResponse
import com.auction.mobile.domain.models.GetUserInfoModel

interface ProfileRepository {

    suspend fun getProfileInfo(): BaseResponse<GetUserInfoModel>
}
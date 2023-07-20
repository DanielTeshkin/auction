package com.auction.mobile.domain.repository

import com.auction.mobile.domain.models.ElectedProductModel

interface MainRepository {

    suspend fun getAllFavorite(): List<ElectedProductModel>

}
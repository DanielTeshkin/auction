package com.example.auctionapp.data.use_case

import android.util.Log
import com.example.auctionapp.domain.models.BaseResponse
import com.example.auctionapp.domain.models.FavoriteProductModel
import com.example.auctionapp.domain.models.toFavoriteModel
import com.example.auctionapp.domain.repository.ProductRepository
import javax.inject.Inject

class GetAllAuctionUseCase @Inject constructor(
    private val repo: ProductRepository
) {

    suspend fun invoke(
        q: String, sort: String,
        price_min: String,
        price_max: String,
        city: String
    ): List<FavoriteProductModel> {
        val favorites = repo.getAllFavorite()
        val result = repo.getProducts(
            q, sort,
            price_min,
            price_max,
            city
        )
        return when (result) {
            is BaseResponse.Success -> {
                when (favorites) {
                    is BaseResponse.Success -> {

                        result.data.map { product ->
                            product.toFavoriteModel(favorites.data.map { it.product.id }
                                .contains(product.id))
                        }
                    }
                    is BaseResponse.Error -> {
                        result.data.map { it.toFavoriteModel(false) }
                    }
                }

            }
            is BaseResponse.Error -> {
                emptyList<FavoriteProductModel>()
            }

        }
    }
}
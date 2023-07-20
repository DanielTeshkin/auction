package com.auction.mobile.data.use_case

import com.auction.mobile.domain.models.BaseResponse
import com.auction.mobile.domain.models.FavoriteProductModel
import com.auction.mobile.domain.models.toFavoriteModel
import com.auction.mobile.domain.repository.ProductRepository
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
        val bids = repo.getBidList()
        return when (result) {
            is BaseResponse.Success -> {
                when (favorites) {
                    is BaseResponse.Success -> {
                        when(bids) {
                            is BaseResponse.Success -> {
                                val currentResult = result.data.filter { item ->
                                    !bids.data.any { bids -> item.id == bids.product.id }
                                }
                                currentResult.map { product ->
                                    product.toFavoriteModel(favorites.data.map { it.product.id }
                                        .contains(product.id))
                                }
                            }
                            is BaseResponse.Error -> {
                                result.data.map { product ->
                                    product.toFavoriteModel(favorites.data.map { it.product.id }
                                        .contains(product.id))
                                }
                            }
                        }

                    }
                    is BaseResponse.Error -> {
                        result.data.map { it.toFavoriteModel(false) }
                    }
                }
            }
            is BaseResponse.Error -> {
                emptyList()
            }

        }
    }
}
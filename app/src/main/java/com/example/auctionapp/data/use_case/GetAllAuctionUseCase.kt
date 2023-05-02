package com.example.auctionapp.data.use_case

import com.example.auctionapp.domain.models.BaseResponse
import com.example.auctionapp.domain.models.FavoriteProductModel
import com.example.auctionapp.domain.models.toFavoriteModel
import com.example.auctionapp.domain.repository.ProductRepository
import javax.inject.Inject

class GetAllAuctionUseCase @Inject constructor(
    private val repo: ProductRepository
) {

    suspend fun invoke(q: String, sort: String): List<FavoriteProductModel> {
        val favorites = repo.getAllFavorite()
        val result = repo.getProducts(q, sort)
        return when (result) {
            is BaseResponse.Success -> {
                result.data.map { product ->
                    product.toFavoriteModel(favorites.map { it.id }.contains(product.id))
                }
            }
            is BaseResponse.Error -> {
                emptyList<FavoriteProductModel>()
            }

        }
    }
}
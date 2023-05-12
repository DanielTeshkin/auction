package com.example.auctionapp.data.use_case

import com.example.auctionapp.domain.models.BaseResponse
import com.example.auctionapp.domain.models.CitiesModel
import com.example.auctionapp.domain.repository.CompletionRepository
import javax.inject.Inject

class GetCitiesUseCase @Inject constructor(
    private val repo: CompletionRepository
) {
    suspend fun invoke(): List<CitiesModel> {
        val result = repo.getCities()
        return when(result) {
            is BaseResponse.Success -> {
                result.data
            }
            is BaseResponse.Error -> {
                emptyList()
            }
        }

    }
}
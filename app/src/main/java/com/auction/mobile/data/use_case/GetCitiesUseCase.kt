package com.auction.mobile.data.use_case

import com.auction.mobile.domain.models.BaseResponse
import com.auction.mobile.domain.models.CitiesModel
import com.auction.mobile.domain.repository.CompletionRepository
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
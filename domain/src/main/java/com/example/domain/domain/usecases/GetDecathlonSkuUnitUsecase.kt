package com.example.domain.domain.usecases

import com.example.domain.domain.models.DecathlonSKUItemBean
import com.example.domain.domain.repository.DecathlonRepository
import com.example.domain.models.ClientResult
import javax.inject.Inject

class GetDecathlonSkuUnitUsecase @Inject constructor(private val repository: DecathlonRepository) {


    suspend fun getInitialSKUItems(
        page: Int
    ): ClientResult<List<DecathlonSKUItemBean>> {
        return repository.getTopHeroProductsInitially(page = page)
    }

    suspend fun sortSKUItems(sortBy: String, page: Int): ClientResult<List<DecathlonSKUItemBean>> {
        return repository.getSortedSKUItems(sortBy = sortBy, page = page)
    }

    suspend fun filterItemsBySearch(
        query: String,
        page: Int
    ): ClientResult<List<DecathlonSKUItemBean>> {
        return repository.getFilteredSKUItems(searchQuery = query, page = page)
    }
}
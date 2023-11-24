package com.example.domain.domain.repository

import com.example.domain.domain.models.DecathlonSKUItemBean
import com.example.domain.domain.models.ListSorters
import com.example.domain.models.ClientResult

interface DecathlonRepository {


    companion object {
        const val USER_ID = 9001
    }

    suspend fun getTopHeroProductsInitially(page: Int): ClientResult<List<DecathlonSKUItemBean>>


    suspend fun getSortedSKUItems(
        page: Int,
        sort: ListSorters
    ): ClientResult<List<DecathlonSKUItemBean>>

    suspend fun getFilteredSKUItems(
        page: Int,
        searchQuery: String
    ): ClientResult<List<DecathlonSKUItemBean>>


}
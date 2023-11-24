package com.example.data.data.service

import com.example.data.data.serverModels.ServerDecathlonSKUItem
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {


    @GET("/api/hero_products")
    suspend fun getHeroProducts(
        query: String? = null,
        sortBy: String? = null,
        page: Int? = 0
    ): Response<List<ServerDecathlonSKUItem>>


}
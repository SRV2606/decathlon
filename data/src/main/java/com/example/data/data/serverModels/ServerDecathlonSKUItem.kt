package com.example.data.data.serverModels

import com.google.gson.annotations.SerializedName

data class ServerDecathlonSKUItem(
    @SerializedName("name")
    val name: String,
    @SerializedName("uId")
    val id: String,
    @SerializedName("price")
    val price: Float,
    @SerializedName("imageUrl")
    val imageUrl: String,
    @SerializedName("brand")
    val brand: String
)



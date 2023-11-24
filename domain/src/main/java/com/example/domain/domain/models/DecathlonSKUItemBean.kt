package com.example.domain.domain.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class DecathlonSKUItemBean(
    val name: String,
    val id: String,
    val price: Float,
    val imageUrl: String,
    val brand: String
) : Parcelable

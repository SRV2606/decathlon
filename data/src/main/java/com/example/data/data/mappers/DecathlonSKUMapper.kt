package com.example.data.data.mappers

import com.example.data.data.serverModels.ServerDecathlonSKUItem
import com.example.domain.domain.models.DecathlonSKUItemBean
import com.example.domain.models.ClientResult
import javax.inject.Inject

class DecathlonSKUMapper @Inject constructor() {
    fun toSkuItem(
        isDummmyTrue: Boolean,
        serverResponse: ClientResult<List<ServerDecathlonSKUItem>>
    ): ClientResult<List<DecathlonSKUItemBean>> {
        return if (!isDummmyTrue)
            serverResponse.let { clientRes ->
                when (clientRes) {
                    is ClientResult.Success -> {
                        val result = clientRes.data.let {
                            it.map { mappedItem ->
                                DecathlonSKUItemBean(
                                    id = mappedItem.id,
                                    name = mappedItem.name,
                                    brand = mappedItem.brand,
                                    imageUrl = mappedItem.imageUrl,
                                    price = mappedItem.price
                                )
                            }
                        }
                        return ClientResult.Success(result)
                    }

                    is ClientResult.Error -> {
                        return ClientResult.Error(clientRes.error)

                    }

                    else -> {
                        ClientResult.InProgress
                    }
                }
            } else {
            val mappedList = toLocalList().map { mappedItem ->
                DecathlonSKUItemBean(
                    id = mappedItem.id,
                    name = mappedItem.name,
                    brand = mappedItem.brand,
                    imageUrl = mappedItem.imageUrl,
                    price = mappedItem.price
                )
            }

            return ClientResult.Success(mappedList)
        }
    }
}


fun toLocalList() = run {
    return@run listOf(
        ServerDecathlonSKUItem(
            name = "Running Shoes",
            id = "SKU001",
            price = 59.99f,
            imageUrl = "https://example.com/shoes1.jpg",
            brand = "Decathlon"
        ),
        ServerDecathlonSKUItem(
            name = "Cycling Helmet",
            id = "SKU002",
            price = 29.99f,
            imageUrl = "https://example.com/helmet1.jpg",
            brand = "Decathlon"
        ),
        ServerDecathlonSKUItem(
            name = "Tennis Racket",
            id = "SKU003",
            price = 49.99f,
            imageUrl = "https://example.com/racket1.jpg",
            brand = "Decathlon"
        ),
        ServerDecathlonSKUItem(
            name = "Yoga Mat",
            id = "SKU004",
            price = 19.99f,
            imageUrl = "https://example.com/mat1.jpg",
            brand = "Decathlon"
        ),
        ServerDecathlonSKUItem(
            name = "Swimming Goggles",
            id = "SKU005",
            price = 15.99f,
            imageUrl = "https://example.com/goggles1.jpg",
            brand = "Decathlon"
        ),
        ServerDecathlonSKUItem(
            name = "Hiking Backpack",
            id = "SKU006",
            price = 39.99f,
            imageUrl = "https://example.com/backpack1.jpg",
            brand = "Decathlon"
        ),
        ServerDecathlonSKUItem(
            name = "Football",
            id = "SKU007",
            price = 24.99f,
            imageUrl = "https://example.com/football1.jpg",
            brand = "Decathlon"
        ),
        ServerDecathlonSKUItem(
            name = "Gym Gloves",
            id = "SKU008",
            price = 12.99f,
            imageUrl = "https://example.com/gloves1.jpg",
            brand = "Decathlon"
        ),
        ServerDecathlonSKUItem(
            name = "Camping Tent",
            id = "SKU009",
            price = 79.99f,
            imageUrl = "https://example.com/tent1.jpg",
            brand = "Decathlon"
        ),
        ServerDecathlonSKUItem(
            name = "Badminton Set",
            id = "SKU010",
            price = 34.99f,
            imageUrl = "https://example.com/badminton1.jpg",
            brand = "Decathlon"
        )
    )
}











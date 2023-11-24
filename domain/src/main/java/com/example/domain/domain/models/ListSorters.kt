package com.example.domain.domain.models


enum class ListSorters(val sortByValue: String) {

    NAME("name"),
    PRICE("price"),
    BRAND("brand"),
    NOTHING("nothing");

    fun convert(): String {
        return when (this) {
            NAME -> NAME.sortByValue
            PRICE -> PRICE.sortByValue
            BRAND -> BRAND.sortByValue
            NOTHING -> NOTHING.sortByValue
        }

    }
}
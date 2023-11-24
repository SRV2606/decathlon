package com.example.decathlon.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.data.mappers.DecathlonSKUMapper
import com.example.domain.domain.models.DecathlonSKUItemBean
import com.example.domain.domain.usecases.GetDecathlonSkuUnitUsecase
import com.example.domain.models.ClientResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class MainViewModel @Inject constructor(
    private val getDecathlonSkuUnitUsecase: GetDecathlonSkuUnitUsecase,
    private val decathlonSKUMapper: DecathlonSKUMapper
) : ViewModel() {


    private val _skuList: MutableStateFlow<ClientResult<List<DecathlonSKUItemBean>>> =
        MutableStateFlow(ClientResult.InProgress)
    val skuList = _skuList.asStateFlow()


    private val _sortedSkuList: MutableStateFlow<ClientResult<List<DecathlonSKUItemBean>>> =
        MutableStateFlow(ClientResult.InProgress)
    val sortedSkuList = _sortedSkuList.asStateFlow()


    private val _filteredSkuList: MutableStateFlow<ClientResult<List<DecathlonSKUItemBean>>> =
        MutableStateFlow(ClientResult.InProgress)
    val filteredSkuList = _filteredSkuList.asStateFlow()


    fun getInitialHeroProductsWithPagination(page: Int) {
        viewModelScope.launch {
            _skuList.emit(ClientResult.InProgress)
            val response = getDecathlonSkuUnitUsecase.getInitialSKUItems(page)
            _skuList.emit(response)
        }
    }


    fun getSortedItemsWithPagination(page: Int, sortBy: String) {
        viewModelScope.launch {
            _sortedSkuList.emit(ClientResult.InProgress)
            val response = getDecathlonSkuUnitUsecase.sortSKUItems(page = page, sortBy = sortBy)
            _sortedSkuList.emit(response)
        }
    }

    fun getFilteredItemsWithPagination(searchQuery: String, page: Int) {
        viewModelScope.launch {
            _filteredSkuList.emit(ClientResult.InProgress)
            val response =
                getDecathlonSkuUnitUsecase.filterItemsBySearch(page = page, query = searchQuery)
            _filteredSkuList.emit(response)
        }
    }

}

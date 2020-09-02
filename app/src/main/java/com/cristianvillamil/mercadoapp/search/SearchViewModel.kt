package com.cristianvillamil.mercadoapp.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cristianvillamil.mercadoapp.network.ApiHelper
import com.cristianvillamil.mercadoapp.network.MainRepository
import com.cristianvillamil.mercadoapp.search.model.SearchResult
import kotlinx.coroutines.launch

class SearchViewModel : ViewModel() {
    private var mainRepository: MainRepository? = null

    private var onItemSearchResponse: MutableLiveData<Result<List<SearchResult>>> =
        MutableLiveData()

    fun getOnItemSearchResponseLiveData(): LiveData<Result<List<SearchResult>>> =
        onItemSearchResponse

    fun setApiHelper(apiHelper: ApiHelper) {
        mainRepository = MainRepository(apiHelper)
    }

    fun searchItem(itemName: String) {
        viewModelScope.launch {
            onItemSearchResponse.value = kotlin.runCatching {
                mainRepository?.getProducts(itemName)?.result ?: emptyList()
            }
        }
    }
}
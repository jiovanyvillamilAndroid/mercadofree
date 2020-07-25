package com.cristianvillamil.mercadoapp.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cristianvillamil.mercadoapp.network.ApiHelper
import com.cristianvillamil.mercadoapp.network.MainRepository
import com.cristianvillamil.mercadoapp.network.SearchResponse
import kotlinx.coroutines.launch

class SearchViewModel : ViewModel() {
    private var mainRepository: MainRepository? = null

    private var onItemSearchResponse: MutableLiveData<MainRepository.Result<SearchResponse?>> =
        MutableLiveData()

    fun getOnItemSearchResponseLiveData(): LiveData<MainRepository.Result<SearchResponse?>> =
        onItemSearchResponse

    fun setApiHelper(apiHelper: ApiHelper) {
        mainRepository = MainRepository(apiHelper)
    }

    fun searchItem(itemName: String) {
        viewModelScope.launch {
            runCatching {
                mainRepository?.let { mainRepository ->
                    onItemSearchResponse.value =
                        MainRepository.Result.Success(mainRepository.getUsers(itemName))
                }
            }.onFailure { exception ->
                onItemSearchResponse.value = MainRepository.Result.Error(exception)
            }
        }
    }
}
package com.cristianvillamil.mercadoapp.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cristianvillamil.mercadoapp.network.ApiHelper
import com.cristianvillamil.mercadoapp.network.MainRepository
import com.cristianvillamil.mercadoapp.network.ProductDetailResponse
import kotlinx.coroutines.launch

class ProductDetailViewModel : ViewModel() {

    private var mainRepository: MainRepository? = null
    private val onItemDetailResponseMutableLiveData =
        MutableLiveData<Result<ProductDetailResponse?>>()

    fun getOnItemDetailResponseLiveData(): LiveData<Result<ProductDetailResponse?>> =
        onItemDetailResponseMutableLiveData

    fun setApiHelper(apiHelper: ApiHelper) {
        mainRepository = MainRepository(apiHelper)
    }

    fun getProductDetail(productId: String) {
        viewModelScope.launch {
            onItemDetailResponseMutableLiveData.value =
                runCatching {
                    mainRepository?.getProductDetail(productId)
                }
        }
    }
}
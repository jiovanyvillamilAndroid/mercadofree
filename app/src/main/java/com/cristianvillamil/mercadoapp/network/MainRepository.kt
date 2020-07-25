package com.cristianvillamil.mercadoapp.network

class MainRepository(private val apiHelper: ApiHelper) {

    sealed class Result<out R> {
        data class Success<out T>(val data: T) : Result<T>()
        data class Error(val throwable: Throwable) : Result<Nothing>()
    }

    suspend fun getUsers(productName: String) = apiHelper.searchProduct(productName)

    suspend fun getProductDetail(productId: String) = apiHelper.getItemDetail(productId)
}
package com.cristianvillamil.mercadoapp.network

class ApiHelper(private val apiService: MercadoLibreService) {

    suspend fun searchProduct(productName: String) = apiService.searchItem(productName)

    suspend fun getItemDetail(productId: String) = apiService.getItemDetail(productId)
}
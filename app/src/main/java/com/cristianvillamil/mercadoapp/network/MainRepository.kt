package com.cristianvillamil.mercadoapp.network

class MainRepository(private val apiHelper: ApiHelper) {

    suspend fun getProducts(productName: String) = apiHelper.searchProduct(productName)

    suspend fun getProductDetail(productId: String) = apiHelper.getItemDetail(productId)
}
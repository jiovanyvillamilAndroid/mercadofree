package com.cristianvillamil.mercadoapp.network

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MercadoLibreService {

    @GET("sites/MCO/search")
    suspend fun searchItem(@Query("q") productName: String): SearchResponse

    @GET("items/{product_id}")
    suspend fun getItemDetail(@Path("product_id") productId: String): ProductDetailResponse
}
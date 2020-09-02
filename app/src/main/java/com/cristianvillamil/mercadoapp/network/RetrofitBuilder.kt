package com.cristianvillamil.mercadoapp.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitBuilder {

    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BaseUrlProvider.baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .client(OkHttpProvider.instance)
            .build()
    }

    val apiService: MercadoLibreService = getRetrofit().create(MercadoLibreService::class.java)
}
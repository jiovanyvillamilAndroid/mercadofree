package com.cristianvillamil.mercadoapp.search.model

import com.google.gson.annotations.SerializedName

data class SearchResult(
    @SerializedName("id")
    val id: String,
    @SerializedName("title")
    val title: String,
    @SerializedName("price")
    val price: Double,
    @SerializedName("thumbnail")
    val thumbnail: String
)
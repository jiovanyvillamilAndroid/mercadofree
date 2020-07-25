package com.cristianvillamil.mercadoapp.network

import com.cristianvillamil.mercadoapp.search.model.SearchResult
import com.google.gson.annotations.SerializedName

data class SearchResponse(
    @SerializedName("site_id")
    val siteId: Any,
    @SerializedName("results")
    val result: List<SearchResult>
)
package com.cristianvillamil.mercadoapp.network

import com.google.gson.annotations.SerializedName

data class ProductDetailResponse(
    val id: String,
    val title: String,
    @SerializedName("available_quantity")
    val availableQuantity: Int,
    val condition: String,
    val price: Double,
    val permalink: String,
    val pictures: List<Picture>
)

data class Picture(
    @SerializedName("secure_url")
    val secureUrl: String
)
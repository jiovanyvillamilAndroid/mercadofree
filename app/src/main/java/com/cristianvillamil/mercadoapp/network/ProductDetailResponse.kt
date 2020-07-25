package com.cristianvillamil.mercadoapp.network

import com.google.gson.annotations.SerializedName

data class ProductDetailResponse(
    @SerializedName("id")
    val id: String,
    @SerializedName("title")
    val title: String,
    @SerializedName("available_quantity")
    val availableQuantity: Int,
    @SerializedName("condition")
    val condition: String,
    @SerializedName("price")
    val price: Double,
    @SerializedName("permalink")
    val permalink: String,
    @SerializedName("pictures")
    val pictures: List<Picture>
)

data class Picture(
    @SerializedName("secure_url")
    val secureUrl: String
)
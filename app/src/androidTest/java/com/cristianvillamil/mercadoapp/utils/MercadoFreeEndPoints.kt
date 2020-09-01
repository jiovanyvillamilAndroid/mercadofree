package com.cristianvillamil.mercadoapp.utils

enum class MercadoFreeEndPoints(val regex: Regex) {
    GET_PRODUCT_ITEM("items/\\d+".toRegex()),
    SEARCH_PRODUCTS("sites/MCO/search\\?q=.+".toRegex()),
    UNKNOWN_PATH(".*".toRegex());

    companion object {
        fun fromRequestPath(path: String) =
            values().find {
                it != UNKNOWN_PATH && path.matches(it.regex)
            } ?: UNKNOWN_PATH

    }
}
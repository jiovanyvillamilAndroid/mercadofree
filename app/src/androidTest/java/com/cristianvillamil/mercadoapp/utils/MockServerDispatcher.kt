package com.cristianvillamil.mercadoapp.utils

import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.RecordedRequest

class UnknownEndPointException(path: String) : RuntimeException("the path $path is unknown")

class MockServerDispatcher(
    private val getProductsMockResponse: (String) -> MockResponse,
    private val getProductDetailsMockResponse: (String) -> MockResponse
) : Dispatcher() {
    override fun dispatch(request: RecordedRequest): MockResponse {
        val path = request.path.orEmpty()
        return when (MercadoFreeEndPoints.fromRequestPath(path.substringAfter("/"))) {
            MercadoFreeEndPoints.GET_PRODUCT_ITEM -> {
                getProductDetailsMockResponse(
                    path.substringAfterLast("/")
                )
            }
            MercadoFreeEndPoints.SEARCH_PRODUCTS -> {
                getProductsMockResponse(path.substringAfter("="))
            }
            MercadoFreeEndPoints.UNKNOWN_PATH -> throw UnknownEndPointException(path)
        }
    }
}
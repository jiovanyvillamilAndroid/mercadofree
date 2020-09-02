package com.cristianvillamil.mercadoapp.screens

import com.agoda.kakao.common.views.KView
import com.agoda.kakao.screen.Screen
import com.agoda.kakao.text.KButton
import com.agoda.kakao.text.KTextView
import com.cristianvillamil.mercadoapp.R

class ProductDetailsScreen : Screen<ProductDetailsScreen>() {

    val pictures: KView = KView {
        withId(R.id.carouselView)
    }

    val title = KTextView {
        withId(R.id.productName)
    }

    val price = KTextView {
        withId(R.id.productPrice)
    }

    val productStatus = KTextView {
        withId(R.id.productState)
    }

    val productQuantity = KTextView {
        withId(R.id.productQuantity)
    }

    val showMoreDetails = KButton {
        withId(R.id.showMoreDetails)
    }

    val errorText = KTextView {
        withId(R.id.errorText)
    }

    val tryAgainButton = KButton {
        withId(R.id.tryAgainButton)
    }

}
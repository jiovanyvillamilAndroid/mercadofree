package com.cristianvillamil.mercadoapp.robots.actions

import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import com.agoda.kakao.text.KButton
import com.agoda.kakao.text.KTextView
import com.cristianvillamil.mercadoapp.matchers.CarouselPicturesMatcher
import com.cristianvillamil.mercadoapp.network.Picture
import com.cristianvillamil.mercadoapp.robots.NoActions
import com.cristianvillamil.mercadoapp.screens.ProductDetailsScreen
import org.hamcrest.Matchers

class ProductDetailsActions {
    private val screen = ProductDetailsScreen()

    fun withOpenedProduct(verifications: ProductDetailsVerifications.() -> NoActions) =
        ProductDetailsVerifications(screen).verifications()

}

class ProductDetailsVerifications(screen: ProductDetailsScreen) {

    private val pictures = screen.pictures
    val productTitle = screen.title
    val productPrice = screen.price
    val productQuantity = screen.productQuantity
    val showMoreDetailsButton = screen.showMoreDetails
    val productStatus = screen.productStatus
    val tryAgainButton = screen.tryAgainButton
    val errorMessage = screen.errorText

    fun verifyShownPictures(expectedPictures: List<Picture>): NoActions {
        pictures {
            isDisplayed()
            assert {
                ViewAssertions.matches(CarouselPicturesMatcher.hasSize(expectedPictures.size))
            }
        }
        return NoActions
    }

    infix fun KTextView.shouldBe(expected: String): NoActions {
        isDisplayed()
        hasText(expected)
        return NoActions
    }

    infix fun KTextView.shouldBe(expected: Int): NoActions {
        isDisplayed()
        hasText(expected)
        return NoActions
    }

    fun KButton.isNotEnabled(): NoActions {
        assert {
            ViewAssertions.matches(Matchers.not(ViewMatchers.isEnabled()))
        }
        return NoActions
    }

}
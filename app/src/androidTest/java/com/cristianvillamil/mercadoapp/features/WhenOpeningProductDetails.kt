package com.cristianvillamil.mercadoapp.features

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.cristianvillamil.mercadoapp.*
import com.cristianvillamil.mercadoapp.network.ProductDetailResponse
import com.cristianvillamil.mercadoapp.robots.mercadoFreeBot
import com.cristianvillamil.mercadoapp.search.recycler_view.toMoneyString
import org.junit.FixMethodOrder
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.MethodSorters

@RunWith(AndroidJUnit4::class)
@FixMethodOrder(value = MethodSorters.NAME_ASCENDING)
class WhenOpeningProductDetails : BaseTest() {

    private val anyValidProductPredicate =
        { index: Int, _: ProductDetailResponse -> index + 1 != ID_OF_FAILED_PRODUCT_DETAILS_RESPONSE.toInt() }

    private val anyProductWithStockPredicate =
        { index: Int, product: ProductDetailResponse -> index + 1 != ID_OF_FAILED_PRODUCT_DETAILS_RESPONSE.toInt() && product.availableQuantity > 0 }

    private val anyProductWithoutStockPredicate =
        { index: Int, product: ProductDetailResponse -> index + 1 != ID_OF_FAILED_PRODUCT_DETAILS_RESPONSE.toInt() && product.availableQuantity == 0 }

    private val anyProductWithNewStatus =
        { index: Int, product: ProductDetailResponse -> index + 1 != ID_OF_FAILED_PRODUCT_DETAILS_RESPONSE.toInt() && product.condition == NEW_PRODUCT_STATUS }

    private val anyProductWithoutNewStatus =
        { index: Int, product: ProductDetailResponse -> index + 1 != ID_OF_FAILED_PRODUCT_DETAILS_RESPONSE.toInt() && product.condition != NEW_PRODUCT_STATUS }

    @Test
    fun userShouldSeeTheInformationOfTheSelectedProduct() {

        val (position, expectedProductDetails) = getProductDetailResponse(anyValidProductPredicate)

        mercadoFreeBot {
            onSearchScreen {
                search(SUCCESS_QUERY)
                registerAnimationIdling(lottieIdlingAnimationResource)
                selectItemAtPosition(position)
            }
            onProductDetails {
                withOpenedProduct {
                    verifyShownPictures(expectedProductDetails.pictures)
                    productTitle shouldBe expectedProductDetails.title
                    productPrice shouldBe expectedProductDetails.price.toMoneyString()
                }
            }
        }
    }

    @Test
    fun userShouldSeeAvailableStockMessageIfSelectedProductQuantityIsGreaterThanZero() {

        val (position, expectedProductDetails) = getProductDetailResponse(
            anyProductWithStockPredicate
        )

        mercadoFreeBot {
            onSearchScreen {
                search(SUCCESS_QUERY)
                registerAnimationIdling(lottieIdlingAnimationResource)
                selectItemAtPosition(position)
            }
            onProductDetails {
                withOpenedProduct {
                    verifyShownPictures(expectedProductDetails.pictures)
                    productTitle shouldBe expectedProductDetails.title
                    productPrice shouldBe expectedProductDetails.price.toMoneyString()
                    showMoreDetailsButton.isEnabled()
                    productQuantity shouldBe R.string.stock_available
                }
            }
        }
    }

    @Test
    fun userShouldSeeNoStockMessageIfSelectedProductQuantityIsZero() {

        val (position, expectedProductDetails) = getProductDetailResponse(
            anyProductWithoutStockPredicate
        )

        mercadoFreeBot {
            onSearchScreen {
                search(SUCCESS_QUERY)
                registerAnimationIdling(lottieIdlingAnimationResource)
                selectItemAtPosition(position)
            }
            onProductDetails {
                withOpenedProduct {
                    verifyShownPictures(expectedProductDetails.pictures)
                    productTitle shouldBe expectedProductDetails.title
                    productPrice shouldBe expectedProductDetails.price.toMoneyString()
                    showMoreDetailsButton.isNotEnabled()
                    productQuantity shouldBe R.string.coming_soon
                }
            }
        }
    }

    @Test
    fun userShouldSeeNewTextIfProductStatusIsNew() {
        val (position, expectedProductDetails) = getProductDetailResponse(
            anyProductWithNewStatus
        )

        mercadoFreeBot {
            onSearchScreen {
                search(SUCCESS_QUERY)
                registerAnimationIdling(lottieIdlingAnimationResource)
                selectItemAtPosition(position)
            }
            onProductDetails {
                withOpenedProduct {
                    verifyShownPictures(expectedProductDetails.pictures)
                    productTitle shouldBe expectedProductDetails.title
                    productPrice shouldBe expectedProductDetails.price.toMoneyString()
                    productStatus shouldBe R.string.new_word
                }
            }
        }
    }

    @Test
    fun userShouldSeeUsedTextIfProductStatusIsNotNew() {
        val (position, expectedProductDetails) = getProductDetailResponse(
            anyProductWithoutNewStatus
        )

        mercadoFreeBot {
            onSearchScreen {
                search(SUCCESS_QUERY)
                registerAnimationIdling(lottieIdlingAnimationResource)
                selectItemAtPosition(position)
            }
            onProductDetails {
                withOpenedProduct {
                    verifyShownPictures(expectedProductDetails.pictures)
                    productTitle shouldBe expectedProductDetails.title
                    productPrice shouldBe expectedProductDetails.price.toMoneyString()
                    productStatus shouldBe R.string.used
                }
            }
        }
    }

    @Test
    fun userShouldSeeAnErrorMessageWhenLoadingProductDetailsFails() {
        val position = ID_OF_FAILED_PRODUCT_DETAILS_RESPONSE.toInt() - 1
        mercadoFreeBot {
            onSearchScreen {
                search(SUCCESS_QUERY)
                registerAnimationIdling(lottieIdlingAnimationResource)
                selectItemAtPosition(position)
            }
            onProductDetails {
                withOpenedProduct {
                    tryAgainButton.isDisplayed()
                    tryAgainButton.isEnabled()
                    errorMessage shouldBe R.string.product_detail_error_message
                }
            }
        }
    }

    private fun getProductDetailResponse(condition: (Int, ProductDetailResponse) -> Boolean) =
        expectedProductsDetails
            .withIndex()
            .asSequence()
            .filter { (i, p) -> condition(i, p) }
            .shuffled()
            .first()
}
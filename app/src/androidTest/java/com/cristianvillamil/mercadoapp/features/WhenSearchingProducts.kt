package com.cristianvillamil.mercadoapp.features

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.cristianvillamil.mercadoapp.*
import com.cristianvillamil.mercadoapp.robots.mercadoFreeBot
import org.junit.FixMethodOrder
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.MethodSorters

@RunWith(AndroidJUnit4::class)
@FixMethodOrder(value = MethodSorters.NAME_ASCENDING)
class WhenSearchingProducts : BaseTest() {

    @Test
    fun userShouldSeeAllTheProductsFoundWithGivenKeyword() {
        mercadoFreeBot {
            onSearchScreen {
                search(SUCCESS_QUERY)
                registerAnimationIdling(lottieIdlingAnimationResource)
                withResults {
                    animationState shouldBe R.id.onLoadSuccess
                    size shouldBe TOTAL_PRODUCTS
                    results shouldBe expectedResults
                }
            }
        }
    }

    @Test
    fun userShouldSeeAnEmptyAnimationWhenNoProductsWereFound() {
        mercadoFreeBot {
            onSearchScreen {
                search(EMPTY_QUERY)
                registerAnimationIdling(lottieIdlingAnimationResource)
                withResults {
                    size shouldBe 0
                    results.isNotDisplayed()
                    animationState shouldBe R.id.onEmpty
                }
            }
        }
    }

    @Test
    fun userShouldSeeAnErrorAnimationWhenFetchingProductsFails() {
        mercadoFreeBot {
            onSearchScreen {
                search(ERROR_QUERY)
                registerAnimationIdling(lottieIdlingAnimationResource)
                withResults {
                    size shouldBe 0
                    results.isNotDisplayed()
                    animationState shouldBe R.id.onError
                }
            }
        }
    }
}
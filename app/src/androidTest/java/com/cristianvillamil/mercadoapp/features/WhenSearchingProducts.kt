package com.cristianvillamil.mercadoapp.features

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.cristianvillamil.mercadoapp.R
import com.cristianvillamil.mercadoapp.robots.mercadoFreeBot
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
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
}
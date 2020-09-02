package com.cristianvillamil.mercadoapp.robots.actions

import androidx.test.espresso.assertion.ViewAssertions
import com.agoda.kakao.common.views.KView
import com.agoda.kakao.recycler.KRecyclerView
import com.cristianvillamil.mercadoapp.R
import com.cristianvillamil.mercadoapp.matchers.MotionLayoutMatcher
import com.cristianvillamil.mercadoapp.robots.NoActions
import com.cristianvillamil.mercadoapp.screens.SearchScreen
import com.cristianvillamil.mercadoapp.search.model.SearchResult
import com.cristianvillamil.mercadoapp.search.recycler_view.toMoneyString

class SearchProductsActions {
    val screen = SearchScreen()

    fun search(query: String) {
        screen {
            searchFieldInput {
                isDisplayed()
                typeText(query)
            }
        }
    }

    fun selectItemAtPosition(position: Int): NoActions {
        screen {
            searchResults {
                scrollTo(position)
                childAt<SearchScreen.SearchResultItem>(position) {
                    click()
                }
            }
        }
        return NoActions
    }

    fun withResults(verifications: SearchProductVerifications.() -> NoActions): NoActions {
        return SearchProductVerifications(screen).verifications()
    }
}

class SearchProductVerifications(private val screen: SearchScreen) {

    val size = screen.searchResults
    val results = screen.searchResults
    val animationState = screen.motionLayoutView

    infix fun KView.shouldBe(expectedAnimationState: Int): NoActions {
        val success = R.id.onLoadSuccess
        screen {
            animation {
                if (expectedAnimationState == success)
                    isNotDisplayed()
                else
                    isDisplayed()
            }
        }
        animationState.assert {
            ViewAssertions.matches(MotionLayoutMatcher.hasMotionState(expectedAnimationState))
        }

        return NoActions
    }

    infix fun KRecyclerView.shouldBe(size: Int): NoActions {
        this{
            if (size > 0)
                isDisplayed()
            hasSize(size)
        }
        return NoActions
    }

    infix fun KRecyclerView.shouldBe(expected: List<SearchResult>): NoActions {
        this{
            expected.forEachIndexed { index, searchResult ->
                scrollTo(index)
                childAt<SearchScreen.SearchResultItem>(index) {
                    productImage.isDisplayed()
                    productName.hasText(searchResult.title)
                    price.hasText(searchResult.price.toMoneyString())
                }
            }
        }
        return NoActions
    }

}
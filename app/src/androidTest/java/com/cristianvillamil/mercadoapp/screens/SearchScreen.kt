package com.cristianvillamil.mercadoapp.screens

import android.view.View
import com.agoda.kakao.common.views.KView
import com.agoda.kakao.edit.KEditText
import com.agoda.kakao.image.KImageView
import com.agoda.kakao.recycler.KRecyclerItem
import com.agoda.kakao.recycler.KRecyclerView
import com.agoda.kakao.screen.Screen
import com.agoda.kakao.text.KTextView
import com.cristianvillamil.mercadoapp.R
import com.cristianvillamil.mercadoapp.search.model.SearchResult
import com.cristianvillamil.mercadoapp.search.recycler_view.toMoneyString
import org.hamcrest.Matcher

class SearchScreen : Screen<SearchScreen>() {
    val searchFieldInput = KEditText {
        withId(R.id.inputSearchEditText)
    }

    val animation =KView{
        withId(R.id.animationView)
    }

    val motionLayoutView = KView{
        withId(R.id.motionLayout)
    }

    val searchResults: KRecyclerView = KRecyclerView({
        withId(R.id.recyclerView)
    }, itemTypeBuilder = {
        itemType(::SearchResultItem)
    })

    class SearchResultItem(parent: Matcher<View>) : KRecyclerItem<SearchResultItem>(parent) {
        val productImage = KImageView(parent) {
            withId(R.id.productImage)
        }

        val productName = KTextView(parent) {
            withId(R.id.productName)
        }
        val price = KTextView(parent) {
            withId(R.id.productPrice)
        }
    }

}
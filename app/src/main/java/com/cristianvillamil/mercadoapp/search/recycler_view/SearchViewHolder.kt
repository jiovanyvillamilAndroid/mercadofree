package com.cristianvillamil.mercadoapp.search.recycler_view

import android.view.View
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.cristianvillamil.mercadoapp.search.SearchFragmentDirections
import com.cristianvillamil.mercadoapp.search.model.SearchResult
import kotlinx.android.synthetic.main.search_item.view.*
import java.text.DecimalFormat

class SearchViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {

    fun bindItem(searchResult: SearchResult) = with(view) {
        productName.text = searchResult.title
        productImage.visibility = View.VISIBLE
        Glide.with(this).load(searchResult.thumbnail).into(productImage)

        productPrice.text = searchResult.price.toMoneyString()
        view.setOnClickListener {
            val directions =
                SearchFragmentDirections
                    .actionSearchFragmentToProductDetailFragment(searchResult.id)
            view.findNavController().navigate(directions)
        }
    }
}

fun Double.toMoneyString(): String {
    val decimalFormat = DecimalFormat("#,##0.00")
    return "$" + decimalFormat.format(this)
}
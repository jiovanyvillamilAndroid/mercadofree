package com.cristianvillamil.mercadoapp.search.recycler_view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.cristianvillamil.mercadoapp.R
import com.cristianvillamil.mercadoapp.search.model.SearchResult

class SearchAdapter : RecyclerView.Adapter<SearchViewHolder>() {

    private var items: List<SearchResult> = arrayListOf()

    fun setItems(items: List<SearchResult>) {
        this.items = items
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.search_item, parent, false)
        return SearchViewHolder(view)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) =
        holder.bindItem(items[position])

}
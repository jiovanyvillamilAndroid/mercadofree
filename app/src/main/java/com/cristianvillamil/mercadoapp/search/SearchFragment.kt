package com.cristianvillamil.mercadoapp.search

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.cristianvillamil.mercadoapp.R
import com.cristianvillamil.mercadoapp.network.ApiHelper
import com.cristianvillamil.mercadoapp.network.MainRepository
import com.cristianvillamil.mercadoapp.network.RetrofitBuilder
import com.cristianvillamil.mercadoapp.network.SearchResponse
import com.cristianvillamil.mercadoapp.search.recycler_view.SearchAdapter
import kotlinx.android.synthetic.main.fragment_search.*


class SearchFragment : Fragment() {

    private val minCharsForSearch = 3
    private val waitTimeForSearch = 700L
    private val searchAdapter = SearchAdapter()
    private val timeHandler = Handler()
    lateinit var searchViewModel: SearchViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_search, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
        initSearchTextListener()
        searchViewModel = ViewModelProvider(this).get(SearchViewModel::class.java)
        searchViewModel.setApiHelper(ApiHelper(RetrofitBuilder.apiService))
        searchViewModel.getOnItemSearchResponseLiveData().observe(viewLifecycleOwner,
            Observer {
                when (it) {
                    is MainRepository.Result.Success<SearchResponse?> -> {
                        val results = it.data?.result.orEmpty()
                        searchAdapter.setItems(results)
                        motionLayout.transitionToState(R.id.onLoadSuccess)
                    }
                }
            })
    }

    private fun initRecyclerView() {
        recyclerView.adapter = searchAdapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
    }

    private fun initSearchTextListener() {
        inputSearchEditText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                //Not necessary
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                //Not necessary
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                p0?.let { it ->
                    val textToSearch = it.toString()
                    timeHandler.removeCallbacksAndMessages(null)
                    timeHandler.postDelayed({
                        when {
                            textToSearch.length >= minCharsForSearch -> {
                                motionLayout.transitionToState(R.id.end)
                                hideKeyboard()
                                searchViewModel.searchItem(textToSearch)
                            }
                            textToSearch.length == 1 || textToSearch.isEmpty() -> {
                                motionLayout.transitionToState(R.id.start)
                            }
                        }

                    }, waitTimeForSearch)

                }
            }
        })
    }

    private fun hideKeyboard() {
        val imm: InputMethodManager =
            requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(requireView().windowToken, 0)
    }

}
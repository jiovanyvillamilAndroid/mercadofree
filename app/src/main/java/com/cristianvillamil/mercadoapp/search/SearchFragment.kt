package com.cristianvillamil.mercadoapp.search

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.cristianvillamil.mercadoapp.R
import com.cristianvillamil.mercadoapp.network.ApiHelper
import com.cristianvillamil.mercadoapp.network.MainRepository
import com.cristianvillamil.mercadoapp.network.RetrofitBuilder
import com.cristianvillamil.mercadoapp.search.model.SearchResult
import com.cristianvillamil.mercadoapp.search.recycler_view.SearchAdapter
import kotlinx.android.synthetic.main.fragment_search.*


class SearchFragment : Fragment() {

    private val minCharsForSearch = 3
    private val waitTimeForSearch = 700L
    private val searchAdapter = SearchAdapter()
    private val timeHandler = Handler(Looper.myLooper()!!)
    private lateinit var searchViewModel: SearchViewModel
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
        initViewModel()
        initOnSearchItemResponseObserver()
    }

    private fun initOnSearchItemResponseObserver() {
        searchViewModel.getOnItemSearchResponseLiveData().observe(viewLifecycleOwner,
            {
                when (it) {
                    is MainRepository.Result.Success<List<SearchResult>> -> {
                        onSearchSuccess(it.data)
                    }
                    is MainRepository.Result.Error -> {
                        showState(false)
                    }
                }
            })
    }

    private fun showState(emptyState: Boolean) {
        recyclerView.visibility = View.GONE
        if (emptyState) {
            animationView.setAnimation(R.raw.empty_state)
            motionLayout.transitionToState(R.id.onEmpty)
        } else {
            animationView.setAnimation(R.raw.error)
            motionLayout.transitionToState(R.id.onError)
        }

        animationView.playAnimation()
    }

    private fun onSearchSuccess(results: List<SearchResult>) {
        if (results.isEmpty()) {
            showState(true)
        } else {
            searchAdapter.setItems(results)
            motionLayout.transitionToState(R.id.onLoadSuccess)
        }
    }

    private fun initViewModel() {
        searchViewModel = ViewModelProvider(this).get(SearchViewModel::class.java)
        searchViewModel.setApiHelper(ApiHelper(RetrofitBuilder.apiService))
    }

    private fun initRecyclerView() {
        recyclerView.adapter = searchAdapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
    }

    private fun initSearchTextListener() {
        inputSearchEditText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                p0?.let { it ->
                    val textToSearch = it.toString()
                    timeHandler.removeCallbacksAndMessages(null)
                    timeHandler.postDelayed({
                        when {
                            textToSearch.length >= minCharsForSearch -> {
                                initSearch(textToSearch)
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

    private fun initSearch(textToSearch: String) {
        animationView.setAnimation(R.raw.search)
        animationView.playAnimation()
        motionLayout.transitionToState(R.id.end)
        hideKeyboard()
        searchViewModel.searchItem(textToSearch)
    }

    private fun hideKeyboard() {
        val imm: InputMethodManager =
            requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(requireView().windowToken, 0)
    }

}
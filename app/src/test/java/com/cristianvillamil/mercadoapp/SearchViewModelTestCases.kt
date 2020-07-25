package com.cristianvillamil.mercadoapp

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.cristianvillamil.mercadoapp.network.ApiHelper
import com.cristianvillamil.mercadoapp.network.MainRepository
import com.cristianvillamil.mercadoapp.network.SearchResponse
import com.cristianvillamil.mercadoapp.search.SearchViewModel
import com.cristianvillamil.mercadoapp.search.model.SearchResult
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import junit.framework.Assert.assertEquals
import junit.framework.Assert.fail
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule


class SearchViewModelTestCases {

    @MockK
    lateinit var apiHelper: ApiHelper

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    private val textToSearch = "Hola Mundo"
    private val viewModel = SearchViewModel()

    @ExperimentalCoroutinesApi
    @Before
    fun before() {
        Dispatchers.setMain(TestCoroutineDispatcher())
        MockKAnnotations.init(this)
    }

    @Test
    fun evaluateSearchError() {
        //Given
        val runtimeException = RuntimeException("Mock error")
        coEvery {
            apiHelper.searchProduct(textToSearch)
        } throws runtimeException
        viewModel.setApiHelper(apiHelper)

        //When
        viewModel.searchItem(textToSearch)

        //Then
        coVerify { apiHelper.searchProduct(textToSearch) }
        viewModel.getOnItemSearchResponseLiveData().observeForever {
            when (it) {
                is MainRepository.Result.Error -> {
                    assertEquals(it.throwable, runtimeException)
                }
                else -> {
                    fail()
                }
            }
        }
    }

    @Test
    fun evaluateSearchSuccess() {
        //Given
        val expectedResult = SearchResponse(
            "MCO", arrayListOf(
                SearchResult(
                    "kash", "Hola Mundo",
                    Double.MAX_VALUE, "testThumbnail"
                )
            )
        )
        coEvery {
            apiHelper.searchProduct(textToSearch)
        } returns expectedResult
        viewModel.setApiHelper(apiHelper)

        //When
        viewModel.searchItem(textToSearch)

        //Then
        coVerify { apiHelper.searchProduct(textToSearch) }
        viewModel.getOnItemSearchResponseLiveData().observeForever {
            when (it) {
                is MainRepository.Result.Success -> {
                    assertEquals(it.data, expectedResult.result)
                }
                else -> {
                    fail()
                }
            }
        }
    }
}
package com.cristianvillamil.mercadoapp

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.cristianvillamil.mercadoapp.detail.ProductDetailViewModel
import com.cristianvillamil.mercadoapp.network.ApiHelper
import com.cristianvillamil.mercadoapp.network.ProductDetailResponse
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.fail
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule

class ProductDetailViewModelTestCases {
    @MockK
    lateinit var apiHelper: ApiHelper

    private val productId = "MCO403726"

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    private val viewModel = ProductDetailViewModel()

    @ExperimentalCoroutinesApi
    @Before
    fun before() {
        Dispatchers.setMain(TestCoroutineDispatcher())
        MockKAnnotations.init(this)
    }

    @ExperimentalCoroutinesApi
    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun evaluateSearchError() {
        //Given
        val runtimeException = RuntimeException("Mock error")
        coEvery {
            apiHelper.getItemDetail(productId)
        } throws runtimeException
        viewModel.setApiHelper(apiHelper)

        //When
        viewModel.getProductDetail(productId)

        //Then
        coVerify { apiHelper.getItemDetail(productId) }
        viewModel.getOnItemDetailResponseLiveData().observeForever { result ->
            result.fold(
                onFailure = { error ->
                    assertEquals(error, runtimeException)
                },
                onSuccess = { fail() }
            )
        }
    }

    @Test
    fun evaluateSearchSuccess() {
        //Given
        val expectedResult = ProductDetailResponse("", "", 1, "", 2.0, "", arrayListOf())
        coEvery {
            apiHelper.getItemDetail(productId)
        } returns expectedResult
        viewModel.setApiHelper(apiHelper)

        //When
        viewModel.getProductDetail(productId)

        //Then
        coVerify { apiHelper.getItemDetail(productId) }
        viewModel.getOnItemDetailResponseLiveData().observeForever { result ->
            result.fold(
                onSuccess = { data ->
                    assertEquals(data, expectedResult)
                },
                onFailure = { fail() }
            )
        }
    }
}
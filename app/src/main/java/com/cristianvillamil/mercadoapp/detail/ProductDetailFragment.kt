package com.cristianvillamil.mercadoapp.detail

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.cristianvillamil.mercadoapp.R
import com.cristianvillamil.mercadoapp.network.*
import com.cristianvillamil.mercadoapp.search.recycler_view.toMoneyString
import kotlinx.android.synthetic.main.fragment_product_detail.*


class ProductDetailFragment : Fragment() {
    private val args: ProductDetailFragmentArgs by navArgs()
    private lateinit var productDetailViewModel: ProductDetailViewModel
    private val newWord = "new"

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_product_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        productDetailViewModel = ViewModelProvider(this)
            .get(ProductDetailViewModel::class.java)
        productDetailViewModel.setApiHelper(ApiHelper(RetrofitBuilder.apiService))
        productDetailViewModel.getProductDetail(args.productId)
        productDetailViewModel.getOnItemDetailResponseLiveData().observe(viewLifecycleOwner,
            Observer {
                errorContainer.visibility = View.GONE
                when (it) {
                    is MainRepository.Result.Success<ProductDetailResponse?> -> {
                        it.data?.let { data ->
                            contentContainer.visibility = View.VISIBLE
                            bindData(data)
                        }
                    }
                    is MainRepository.Result.Error -> {
                        showError()
                    }
                }
            })
    }

    private fun showError() {
        contentContainer.visibility = View.GONE
        errorContainer.visibility = View.VISIBLE
        tryAgainButton.setOnClickListener {
            productDetailViewModel.getProductDetail(args.productId)
        }
    }

    private fun bindData(productDetailResponse: ProductDetailResponse) {
        productName.text = productDetailResponse.title
        productPrice.text = productDetailResponse.price.toMoneyString()
        bindCondition(productDetailResponse.condition)
        bindProductAvailable(
            productDetailResponse.availableQuantity,
            productDetailResponse.permalink
        )
        initCarousel(productDetailResponse.pictures)
    }

    private fun initCarousel(pictures: List<Picture>) {
        carouselView.setImageListener { position, imageView ->
            imageView.scaleType = ImageView.ScaleType.FIT_CENTER
            imageView.setBackgroundColor(
                ContextCompat.getColor(
                    requireContext(),
                    android.R.color.white
                )
            )
            Glide
                .with(requireContext())
                .load(pictures[position].secureUrl)
                .fitCenter()
                .into(imageView)
        }
        carouselView.pageCount = pictures.size
    }

    private fun bindProductAvailable(availableQuantity: Int, permalink: String) {
        if (availableQuantity > 0) {
            productQuantity.setText(R.string.stock_available)
            showMoreDetails.isEnabled = true
            showMoreDetails.setOnClickListener {
                val intent = Intent(Intent.ACTION_VIEW)
                intent.data = Uri.parse(permalink)
                startActivity(intent)
            }
        } else {
            showMoreDetails.isEnabled = false
            productQuantity.setText(R.string.coming_soon)
        }
    }

    private fun bindCondition(productCondition: String) {
        if (productCondition == newWord) {
            productState.setBackgroundResource(R.drawable.rounded_green_background)
            productState.setText(R.string.new_word)
        } else {
            productState.setBackgroundResource(R.drawable.rounded_orange_background)
            productState.setText(R.string.used)
        }
    }
}
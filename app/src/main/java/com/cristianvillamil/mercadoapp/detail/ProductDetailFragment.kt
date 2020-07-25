package com.cristianvillamil.mercadoapp.detail

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
import com.cristianvillamil.mercadoapp.network.ApiHelper
import com.cristianvillamil.mercadoapp.network.MainRepository
import com.cristianvillamil.mercadoapp.network.ProductDetailResponse
import com.cristianvillamil.mercadoapp.network.RetrofitBuilder
import com.cristianvillamil.mercadoapp.search.recycler_view.toMoneyString
import kotlinx.android.synthetic.main.fragment_product_detail.*

class ProductDetailFragment : Fragment() {
    private val args: ProductDetailFragmentArgs by navArgs()
    private lateinit var productDetailViewModel: ProductDetailViewModel

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
                when (it) {
                    is MainRepository.Result.Success<ProductDetailResponse?> -> {
                        it.data?.let { data ->
                            productName.text = data.title
                            productPrice.text = data.price.toMoneyString()
                            if (data.condition == "new") {
                                productState.text = "nuevo"
                            } else {
                                productState.text = "usado"
                            }
                            val pictures = data.pictures
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
                    }
                    is MainRepository.Result.Error -> {
                        it.throwable.printStackTrace()
                    }
                }
            })
    }
}
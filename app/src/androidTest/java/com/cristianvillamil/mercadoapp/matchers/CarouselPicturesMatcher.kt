package com.cristianvillamil.mercadoapp.matchers

import android.view.View
import androidx.test.espresso.matcher.BoundedMatcher
import com.synnapps.carouselview.CarouselView
import org.hamcrest.Description

class CarouselPicturesMatcher(private val expectedImagesSize: Int) :
    BoundedMatcher<View, CarouselView>(CarouselView::class.java) {
    override fun describeTo(description: Description) {
        description.appendText("Carousel number of images should be $expectedImagesSize")
    }

    override fun matchesSafely(item: CarouselView?): Boolean {
        return item?.pageCount == expectedImagesSize
    }

    companion object {
        fun hasSize(expectedImagesSize: Int) = CarouselPicturesMatcher(expectedImagesSize)
    }
}
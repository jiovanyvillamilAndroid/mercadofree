package com.cristianvillamil.mercadoapp.matchers

import android.view.View
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.test.espresso.matcher.BoundedMatcher
import org.hamcrest.Description

class MotionLayoutMatcher(private val expectedState: Int) : BoundedMatcher<View, MotionLayout>(MotionLayout::class.java) {
    override fun describeTo(description: Description) {
        description.appendText("Motion animation state should be $expectedState")
    }

    override fun describeMismatch(item: Any?, description: Description) {
        item as MotionLayout
        description.appendText("Got state ${item.currentState}")
    }
    override fun matchesSafely(item: MotionLayout?): Boolean {
        item?.animation
        return item?.currentState == expectedState
    }

    companion object {
        fun hasMotionState(state: Int) = MotionLayoutMatcher(state)
    }
}
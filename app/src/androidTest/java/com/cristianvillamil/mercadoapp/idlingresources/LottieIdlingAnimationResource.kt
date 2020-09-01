package com.cristianvillamil.mercadoapp.idlingresources

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.IdlingResource
import com.airbnb.lottie.LottieAnimationView

class LottieIdlingAnimationResource(
    private val animationView: LottieAnimationView?,
    private val name: String = "Lottie"
) :
    IdlingResource {

    init {
        animationView?.addAnimatorListener(object : AnimatorListenerAdapter() {
            override fun onAnimationStart(animation: Animator) {
                isIdle = false
            }

            override fun onAnimationEnd(animation: Animator) {
                isIdle = true
                callback?.onTransitionToIdle()
            }
        })
    }

    private var callback: IdlingResource.ResourceCallback? = null
    private var isIdle = false


    override fun getName() = name

    override fun isIdleNow() = isIdle

    override fun registerIdleTransitionCallback(callback: IdlingResource.ResourceCallback?) {
        this.callback = callback
        if (isIdle) callback?.onTransitionToIdle()
    }
    fun removeListeners(){
        animationView?.removeAllAnimatorListeners()
    }
}

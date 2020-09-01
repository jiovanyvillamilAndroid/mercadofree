package com.cristianvillamil.mercadoapp.idlingresources

import androidx.test.espresso.IdlingResource
import okhttp3.Dispatcher
import okhttp3.OkHttpClient

class OkHttp3CustomIdlingResource(private val resourceName: String, okHttp3Client: OkHttpClient) :
    IdlingResource {
    @Volatile
    private var callback: IdlingResource.ResourceCallback? = null
    private val dispatcher: Dispatcher = okHttp3Client.dispatcher

    init {
        dispatcher.idleCallback = Runnable {
            callback?.onTransitionToIdle()
        }
    }

    override fun getName() = resourceName

    override fun isIdleNow(): Boolean {
        println(dispatcher.runningCalls().size)
        return dispatcher.runningCallsCount() == 0
    }


    override fun registerIdleTransitionCallback(callback: IdlingResource.ResourceCallback?) {
        this.callback = callback
    }
}
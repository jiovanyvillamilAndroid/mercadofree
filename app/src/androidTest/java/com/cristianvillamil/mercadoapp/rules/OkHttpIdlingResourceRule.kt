package com.cristianvillamil.mercadoapp.rules

import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.IdlingResource
import com.cristianvillamil.mercadoapp.idlingresources.OkHttp3CustomIdlingResource
import com.cristianvillamil.mercadoapp.network.OkHttpProvider
import org.junit.rules.TestRule
import org.junit.runner.Description
import org.junit.runners.model.Statement

class OkHttpIdlingResourceRule : TestRule {
    private val resource: IdlingResource =
        OkHttp3CustomIdlingResource("MyOkhttp", OkHttpProvider.instance)

    override fun apply(base: Statement, description: Description): Statement {
        return object : Statement() {
            override fun evaluate() {
                IdlingRegistry.getInstance().register(resource)
                base.evaluate()
                IdlingRegistry.getInstance().unregister(resource)
            }
        }
    }
}
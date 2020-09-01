package com.cristianvillamil.mercadoapp.robots

import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.IdlingResource

@DslMarker
annotation class ActionsMarker

@ActionsMarker
interface Actions {
    fun registerAnimationIdling(idlingResource: IdlingResource?) {
        IdlingRegistry.getInstance().register(idlingResource)
    }
}

object NoActions : Actions
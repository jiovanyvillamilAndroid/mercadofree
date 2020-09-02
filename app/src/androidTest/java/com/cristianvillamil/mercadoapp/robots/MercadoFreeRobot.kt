package com.cristianvillamil.mercadoapp.robots

import com.cristianvillamil.mercadoapp.robots.actions.ProductDetailsActions
import com.cristianvillamil.mercadoapp.robots.actions.SearchProductsActions

class MercadoFreeRobot : Actions {
    fun onSearchScreen(action: SearchProductsActions.() -> Actions) =
        SearchProductsActions().action()

    fun onProductDetails(action: ProductDetailsActions.() -> Actions) =
        ProductDetailsActions().action()
}

fun mercadoFreeBot(actions: MercadoFreeRobot.() -> Actions) = MercadoFreeRobot().actions()
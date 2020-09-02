package com.cristianvillamil.mercadoapp.features

import org.junit.runner.RunWith
import org.junit.runners.Suite

@RunWith(Suite::class)
@Suite.SuiteClasses(
    WhenSearchingProducts::class,
    WhenOpeningProductDetails::class
)
class MercadoFreeTestSuite
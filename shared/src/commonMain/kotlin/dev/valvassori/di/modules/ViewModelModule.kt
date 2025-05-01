package dev.valvassori.di.modules

import dev.valvassori.presentation.cart.CartViewModel
import dev.valvassori.presentation.home.HomeViewModel
import dev.valvassori.presentation.merchant.MerchantDetailsViewModel
import org.koin.dsl.module

val viewModelModule = module {
    factory { HomeViewModel(get()) }
    factory { MerchantDetailsViewModel(get()) }
    factory { CartViewModel() }
}

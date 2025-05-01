package dev.valvassori.di

import dev.valvassori.presentation.merchant.MerchantDetailsViewModel
import org.koin.core.module.Module
import org.koin.dsl.module

val merchantModule: Module = module {
    factory { MerchantDetailsViewModel() }
} 
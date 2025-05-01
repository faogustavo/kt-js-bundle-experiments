package dev.valvassori.di.modules

import dev.valvassori.presentation.home.HomeViewModel
import org.koin.dsl.module

val viewModelModule = module {
    factory {
        HomeViewModel(
            merchantRepository = get()
        )
    }
}
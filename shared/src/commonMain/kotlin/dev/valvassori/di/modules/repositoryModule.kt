package dev.valvassori.di.modules

import dev.valvassori.repository.MerchantRepository
import org.koin.dsl.module

internal val repositoryModule = module {
    factory { MerchantRepository(get()) }
}

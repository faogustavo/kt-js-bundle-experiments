package dev.valvassori.di.modules

import dev.valvassori.api.ApiClient
import dev.valvassori.api.ApiService
import org.koin.core.qualifier.named
import org.koin.dsl.module

/**
 * Main Koin DI module for the application.
 * This module provides dependencies for the API layer.
 */
internal val apiModule =
    module {
        // Factory for creating HttpClient instances
        factory { ApiClient.create(get()) }

        // Factory for creating ApiService instances
        factory { ApiService(get(), get(named("API_URL"))) }
    }

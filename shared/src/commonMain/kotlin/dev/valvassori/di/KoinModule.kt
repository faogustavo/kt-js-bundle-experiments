package dev.valvassori.di

import dev.valvassori.di.modules.apiModule
import dev.valvassori.di.modules.repositoryModule
import org.koin.core.KoinApplication
import org.koin.core.context.startKoin
import org.koin.core.qualifier.named
import org.koin.dsl.module

/**
 * Initialize Koin for the application.
 * This function should be called at the application startup.
 *
 * @param additionalModules Additional modules to include in the DI container
 */
internal fun initKoin(
    baseApiUrl: String?,
    builder: KoinApplication.() -> Unit,
) {
    print("Starting Koin!")
    startKoin {
        builder()

        modules(
            module {
                single(named("API_URL")) { baseApiUrl }
            },
            apiModule,
            repositoryModule,
        )
    }
}

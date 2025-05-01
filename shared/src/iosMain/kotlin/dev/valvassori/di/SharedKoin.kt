package dev.valvassori.di

import co.touchlab.skie.configuration.annotations.DefaultArgumentInterop
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.darwin.Darwin
import org.koin.dsl.module

@ObjCName("SharedKoin")
object SharedKoin {
    @DefaultArgumentInterop.Enabled
    fun initKoin(baseApiUrl: String = "http://localhost:8080") {
        initKoin {
            modules(
                module {
                    single<HttpClientEngine> { Darwin.create() }
                },
            )
        }
    }
}

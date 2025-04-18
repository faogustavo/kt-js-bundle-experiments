package dev.valvassori.di

import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.android.Android
import org.koin.dsl.module

object SharedKoin {
    fun initKoin(baseApiUrl: String = "http://localhost:8080") {
        initKoin(baseApiUrl) {
            modules(
                module {
                    single<HttpClientEngine> { Android.create() }
                }
            )
        }
    }
}


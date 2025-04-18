package dev.valvassori.di

import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.js.Js
import org.koin.dsl.module

@JsExport
object SharedKoin {
    fun initKoin(baseApiUrl: String = "http://localhost:8080") {
        initKoin(baseApiUrl) {
            modules(
                module {
                    single<HttpClientEngine> { Js.create() }
                }
            )
        }
    }
}

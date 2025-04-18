package dev.valvassori

import dev.valvassori.routes.configureMerchantRoutes
import dev.valvassori.routes.configureRootRoutes
import io.ktor.serialization.kotlinx.json.json
import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import io.ktor.server.plugins.contentnegotiation.ContentNegotiation
import io.ktor.server.routing.routing
import kotlinx.serialization.json.Json

const val SERVER_PORT = 8080

fun main() {
    embeddedServer(
        factory = Netty, port = SERVER_PORT, host = "0.0.0.0", module = Application::module
    ).start(wait = true)
}

fun Application.module() {
    install(ContentNegotiation) {
        json(Json {
            prettyPrint = true
            isLenient = true
        })
    }

    // Configure routes
    routing {
        configureRootRoutes()
        configureMerchantRoutes()
    }
}

package dev.valvassori.routes

import io.ktor.server.application.call
import io.ktor.server.response.respondText
import io.ktor.server.routing.Routing
import io.ktor.server.routing.get

/**
 * Configures the root route for the application.
 */
fun Routing.configureRootRoutes() {
    get("/") {
        call.respondText("Hello, Ktor!")
    }
}

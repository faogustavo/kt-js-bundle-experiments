package dev.valvassori.routes

import dev.valvassori.service.MerchantService
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.call
import io.ktor.server.response.respond
import io.ktor.server.response.respondText
import io.ktor.server.routing.Routing
import io.ktor.server.routing.get
import io.ktor.server.routing.route

/**
 * Configures the merchant routes for the application.
 */
fun Routing.configureMerchantRoutes() {
    // Group merchant routes
    route("/merchants") {
        // Get all merchants
        get {
            // Use the service to get all merchants with menus and working hours removed to reduce payload size
            call.respond(MerchantService.getAllMerchantsForApiResponse())
        }

        // Get merchant by ID
        get("/{id}") {
            val id = call.parameters["id"] ?: ""
            val merchant = MerchantService.getMerchantById(id)

            if (merchant != null) {
                call.respond(merchant)
            } else {
                call.respondText("Merchant not found", status = HttpStatusCode.NotFound)
            }
        }
    }
}

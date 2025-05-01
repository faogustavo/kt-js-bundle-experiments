package dev.valvassori.api

import dev.valvassori.domain.MerchantResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.http.contentType

/**
 * A service class that connects to the server module APIs.
 *
 * @param client The HTTP client to use for API requests
 * @param baseUrl The base URL of the API server
 */
class ApiService(
    private val client: HttpClient,
    private val baseUrl: String,
) {
    /**
     * Fetches all merchants from the server.
     * The merchants will have empty menus and working hours to reduce payload size.
     *
     * @return A list of simplified merchants
     */
    suspend fun getAllMerchants(): Array<MerchantResponse> =
        try {
            client
                .get("$baseUrl/merchants") {
                    contentType(ContentType.Application.Json)
                }.body()
        } catch (e: Exception) {
            // Log the error or handle it as needed
            emptyArray()
        }

    /**
     * Fetches a merchant by ID from the server.
     *
     * @param id The ID of the merchant to fetch
     * @return The merchant with the specified ID, or null if not found or an error occurred
     */
    suspend fun getMerchantById(id: String): MerchantResponse? =
        try {
            val response =
                client.get("$baseUrl/merchants/$id") {
                    contentType(ContentType.Application.Json)
                }

            if (response.status == HttpStatusCode.OK) {
                response.body()
            } else {
                null
            }
        } catch (e: Exception) {
            // Log the error or handle it as needed
            null
        }
}

package dev.valvassori.api

import io.ktor.client.HttpClient
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

/**
 * A shared HTTP client for making API requests across all platforms.
 */
object ApiClient {
    /**
     * Creates and configures a Ktor HttpClient with common settings and a specific engine.
     *
     * @param engine The HttpClientEngine to use for the client
     * @return A configured HttpClient instance
     */
    fun create(engine: HttpClientEngine): HttpClient =
        HttpClient(engine) {
            // Install content negotiation for JSON serialization/deserialization
            install(ContentNegotiation) {
                json(
                    Json {
                        prettyPrint = true
                        isLenient = true
                        ignoreUnknownKeys = true
                    },
                )
            }

            // Install logging for debugging
            install(Logging) {
                level = LogLevel.ALL
            }
        }
}

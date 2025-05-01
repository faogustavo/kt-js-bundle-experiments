package dev.valvassori.repository

import dev.valvassori.api.ApiService
import dev.valvassori.domain.MerchantResponse

/**
 * Example class that demonstrates how to use Koin for dependency injection.
 * This class uses Koin to get an instance of ApiService.
 */
class MerchantRepository(
    private val apiService: ApiService,
) {
    /**
     * Fetches all merchants from the API.
     *
     * @return A list of merchants
     */
    suspend fun getAllMerchants(): Array<MerchantResponse> = apiService.getAllMerchants()

    /**
     * Fetches a merchant by ID from the API.
     *
     * @param id The ID of the merchant to fetch
     * @return The merchant with the specified ID, or null if not found
     */
    suspend fun getMerchantById(id: String): MerchantResponse? = apiService.getMerchantById(id)
}

package dev.valvassori.service

import dev.valvassori.domain.Merchant
import dev.valvassori.fake.FakeMerchants

/**
 * Service class for handling Merchant-related operations.
 * Acts as the connection point between the storage (FakeMerchants) and the Application.
 */
object MerchantService {
    /**
     * Gets all merchants from the storage.
     *
     * @return A collection of all merchants
     */
    fun getAllMerchants(): Collection<Merchant> = FakeMerchants.allMerchants.values

    /**
     * Gets a merchant by ID from the storage.
     *
     * @param id The ID of the merchant to retrieve
     * @return The merchant with the specified ID, or null if not found
     */
    fun getMerchantById(id: String): Merchant? = FakeMerchants.allMerchants[id]

    /**
     * Gets all merchants from the storage, simplified for API response.
     * The merchants will have empty menus and working hours to reduce payload size.
     *
     * @return A list of simplified merchants
     */
    fun getAllMerchantsForApiResponse(): List<Merchant> = simplifyForApiResponse(getAllMerchants())

    /**
     * Simplifies a Merchant object by removing the menu and workingHours to reduce payload size.
     * Used for list views where detailed menu information and working hours are not needed.
     *
     * @param merchant The original merchant object
     * @return A new Merchant object with the same properties but without the menu and workingHours
     */
    private fun simplifyForApiResponse(merchant: Merchant): Merchant = merchant.copy(menu = emptyList(), workingHours = emptyList())

    /**
     * Simplifies a collection of Merchant objects by removing their menus and workingHours.
     *
     * @param merchants The collection of merchant objects to simplify
     * @return A list of simplified Merchant objects without menus and workingHours
     */
    private fun simplifyForApiResponse(merchants: Collection<Merchant>): List<Merchant> = merchants.map { simplifyForApiResponse(it) }
}

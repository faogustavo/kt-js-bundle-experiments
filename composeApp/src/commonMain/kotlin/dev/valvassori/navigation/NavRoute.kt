package dev.valvassori.navigation

import kotlinx.serialization.Serializable

/**
 * Type-safe route definitions for navigation
 */
@Serializable
object Home

@Serializable
data class MerchantDetails(val merchantId: String)

@Serializable
data class MenuItemDetail(val merchantId: String, val itemId: String)

@Serializable
object Cart 
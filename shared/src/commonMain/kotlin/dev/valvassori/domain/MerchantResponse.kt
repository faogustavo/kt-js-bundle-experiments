@file:JsExport

package dev.valvassori.domain

import kotlinx.serialization.Serializable
import kotlin.js.JsExport

@Serializable
class MerchantResponse(
    val id: String,
    val name: String,
    val type: TypeResponse,
    val minimumOrder: Int,
    val isOpen: Boolean,
    val workingHours: Array<WorkingHoursResponse>,
    val deliveryTime: Int,
    val deliveryFee: Int,
    val rating: Int, // 0..50
    val ratingCount: Int,
    val imageUrl: String,
    val address: AddressResponse,
    val phoneNumber: String,
    val category: String,
    val menu: Array<MenuCategoryResponse>,
) {
    @Serializable
    enum class TypeResponse {
        Supermarket,
        Restaurant,
    }

    @Serializable
    class MenuCategoryResponse(
        val id: String,
        val name: String,
        val description: String?,
        val imageUrl: String?,
        val items: Array<ItemResponse>,
    )
}

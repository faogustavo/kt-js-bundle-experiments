@file:JsExport

package dev.valvassori.domain

import kotlinx.serialization.Serializable
import kotlin.js.JsExport

@Serializable
data class MerchantResponse(
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
    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + name.hashCode()
        result = 31 * result + type.hashCode()
        result = 31 * result + minimumOrder
        result = 31 * result + isOpen.hashCode()
        result = 31 * result + workingHours.contentHashCode()
        result = 31 * result + deliveryTime
        result = 31 * result + deliveryFee
        result = 31 * result + rating
        result = 31 * result + ratingCount
        result = 31 * result + imageUrl.hashCode()
        result = 31 * result + address.hashCode()
        result = 31 * result + phoneNumber.hashCode()
        result = 31 * result + category.hashCode()
        result = 31 * result + menu.contentHashCode()
        return result
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is MerchantResponse) return false

        if (id != other.id) return false
        if (name != other.name) return false
        if (type != other.type) return false
        if (minimumOrder != other.minimumOrder) return false
        if (isOpen != other.isOpen) return false
        if (!workingHours.contentEquals(other.workingHours)) return false
        if (deliveryTime != other.deliveryTime) return false
        if (deliveryFee != other.deliveryFee) return false
        if (rating != other.rating) return false
        if (ratingCount != other.ratingCount) return false
        if (imageUrl != other.imageUrl) return false
        if (address != other.address) return false
        if (phoneNumber != other.phoneNumber) return false
        if (category != other.category) return false
        if (!menu.contentEquals(other.menu)) return false

        return true
    }

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

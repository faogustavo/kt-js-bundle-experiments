package dev.valvassori.domain

import kotlinx.serialization.Serializable

@Serializable
data class Merchant(
    val id: String,
    val name: String,
    val type: Type,
    val minimumOrder: Int,
    val isOpen: Boolean,
    val workingHours: List<WorkingHours>,
    val deliveryTime: Int,
    val deliveryFee: Int,
    val rating: Int, // 0..50
    val ratingCount: Int,
    val imageUrl: String,
    val address: Address,
    val phoneNumber: String,
    val category: String,
    val menu: List<MenuCategory>,
) {
    @Serializable
    enum class Type {
        Supermarket,
        Restaurant,
    }

    @Serializable
    data class MenuCategory(
        val id: String,
        val name: String,
        val description: String?,
        val imageUrl: String?,
        val items: List<Item>,
    )
}

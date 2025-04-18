package dev.valvassori.domain

import kotlinx.serialization.Serializable

@Serializable
data class Item(
    val id: String,
    val name: String,
    val description: String,
    val price: Int,
    val imageUrl: String,
    val options: List<Option>,
    val isAvailable: Boolean,
) {
    @Serializable
    data class Option(
        val id: Long,
        val name: String,
        val description: String,
        val price: Int,
        val isAvailable: Boolean,
        val type: Type,
        val options: List<Entry>,
    ) {
        @Serializable
        enum class Type {
            Boolean,
            SingleSelection,
            MultipleSelection;
        }

        @Serializable
        data class Entry(
            val id: Long,
            val name: String,
            val description: String,
            val price: Int,
        )
    }
}

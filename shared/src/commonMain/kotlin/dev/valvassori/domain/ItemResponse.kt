@file:JsExport

package dev.valvassori.domain

import kotlinx.serialization.Serializable
import kotlin.js.JsExport

@Serializable
@JsExport
data class ItemResponse(
    val id: String,
    val name: String,
    val description: String,
    val price: Int,
    val imageUrl: String,
    val options: Array<OptionResponse>,
    val isAvailable: Boolean,
) {
    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + name.hashCode()
        result = 31 * result + description.hashCode()
        result = 31 * result + price
        result = 31 * result + imageUrl.hashCode()
        result = 31 * result + options.contentHashCode()
        result = 31 * result + isAvailable.hashCode()
        return result
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is ItemResponse) return false

        if (id != other.id) return false
        if (name != other.name) return false
        if (description != other.description) return false
        if (price != other.price) return false
        if (imageUrl != other.imageUrl) return false
        if (!options.contentEquals(other.options)) return false
        if (isAvailable != other.isAvailable) return false

        return true
    }

    @Serializable
    data class OptionResponse(
        val id: String,
        val name: String,
        val description: String,
        val price: Int,
        val isAvailable: Boolean,
        val type: TypeResponse,
        val options: Array<EntryResponse>,
    ) {
        @Serializable
        enum class TypeResponse {
            Boolean,
            SingleSelection,
            MultipleSelection,
        }

        @Serializable
        class EntryResponse(
            val id: String,
            val name: String,
            val description: String,
            val price: Int,
        )

        override fun hashCode(): Int {
            var result = id.hashCode()
            result = 31 * result + name.hashCode()
            result = 31 * result + description.hashCode()
            result = 31 * result + price
            result = 31 * result + isAvailable.hashCode()
            result = 31 * result + type.hashCode()
            result = 31 * result + options.contentHashCode()
            return result
        }

        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (other !is OptionResponse) return false

            if (id != other.id) return false
            if (name != other.name) return false
            if (description != other.description) return false
            if (price != other.price) return false
            if (isAvailable != other.isAvailable) return false
            if (type != other.type) return false
            if (!options.contentEquals(other.options)) return false

            return true
        }
    }
}

@file:JsExport

package dev.valvassori.domain

import kotlinx.serialization.Serializable
import kotlin.js.JsExport

@Serializable
@JsExport
class ItemResponse(
    val id: String,
    val name: String,
    val description: String,
    val price: Int,
    val imageUrl: String,
    val options: Array<OptionResponse>,
    val isAvailable: Boolean,
) {
    @Serializable
    class OptionResponse(
        val id: Long,
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
            MultipleSelection;
        }

        @Serializable
        class EntryResponse(
            val id: Long,
            val name: String,
            val description: String,
            val price: Int,
        )
    }
}

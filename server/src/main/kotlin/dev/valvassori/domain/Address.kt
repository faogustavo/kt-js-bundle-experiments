package dev.valvassori.domain

import kotlinx.serialization.Serializable

@Serializable
data class Address(
    val id: String,
    val name: String,
    val latitude: Double,
    val longitude: Double,
    val addressLine1: String,
    val addressLine2: String,
    val city: String,
    val state: String,
    val zip: String,
)

package dev.valvassori.presentation.cart

import androidx.lifecycle.ViewModel
import dev.valvassori.domain.ItemResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.datetime.Clock

data class CartItem(
    val id: Long,
    val quantity: Int,
    val selectedOptions: Map<ItemResponse.OptionResponse, Any?>,
    val readableOptions: Map<String, Int>,
    val observation: String,
    val subtotal: Int,
    val item: ItemResponse,
)

data class CartState(
    val items: List<CartItem> = emptyList(),
    val isOpen: Boolean = false,
    val totalItems: Int = 0,
    val totalPrice: Int = 0,
    val merchantDeliveryFee: Int = 0,
    val merchantId: String? = null,
    val merchantName: String? = null,
    val merchantCategory: String? = null,
    val merchantDeliveryTime: Int? = null,
    val error: String? = null,
)

sealed class CartError {
    object DifferentMerchant : CartError()

    object UnknownError : CartError()
}

class CartViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(CartState())
    val uiState: StateFlow<CartState> = _uiState.asStateFlow()

    private fun generateReadableOptions(
        item: ItemResponse,
        selectedOptions: Map<ItemResponse.OptionResponse, Any?>,
    ): Map<String, Int> {
        val readableOptions = mutableMapOf<String, Int>()

        selectedOptions.forEach { (option, value) ->
            when (value) {
                is ItemResponse.OptionResponse.EntryResponse -> {
                    readableOptions[value.name] = value.price
                }
                is Set<*> -> {
                    @Suppress("UNCHECKED_CAST")
                    (value as Set<ItemResponse.OptionResponse.EntryResponse>).forEach {
                        readableOptions[it.name] = (readableOptions[it.name] ?: 0) + it.price
                    }
                }
                is Boolean -> {
                    if (value) {
                        readableOptions[option.name] = option.price
                    }
                }
            }
        }

        return readableOptions
    }

    private fun calculateSubtotal(
        item: ItemResponse,
        quantity: Int,
        selectedOptions: Map<ItemResponse.OptionResponse, Any?>,
    ): Int {
        val basePrice = item.price
        val optionsPrice =
            selectedOptions.entries.sumOf { (option, value) ->
                when (value) {
                    is ItemResponse.OptionResponse.EntryResponse -> value.price
                    is Set<*> -> {
                        @Suppress("UNCHECKED_CAST")
                        (value as Set<ItemResponse.OptionResponse.EntryResponse>).sumOf { it.price }
                    }
                    is Boolean -> if (value) option.price else 0
                    else -> 0
                }
            }

        return (basePrice + optionsPrice) * quantity
    }

    fun openCart() {
        _uiState.update { it.copy(isOpen = true) }
    }

    fun closeCart() {
        _uiState.update { it.copy(isOpen = false) }
    }

    fun toggleCart() {
        _uiState.update { it.copy(isOpen = !it.isOpen) }
    }

    fun addItem(
        item: ItemResponse,
        newMerchantId: String,
        newMerchantName: String,
        quantity: Int = 1,
        selectedOptions: Map<ItemResponse.OptionResponse, Any?>,
        observation: String,
        merchantDeliveryFee: Int? = null,
        merchantCategory: String? = null,
        merchantDeliveryTime: Int? = null,
    ): CartError? {
        // Check if the cart is empty or from the same merchant
        if (_uiState.value.items.isEmpty()) {
            // Cart is empty, set the merchant ID and name
            _uiState.update {
                it.copy(
                    merchantId = newMerchantId,
                    merchantName = newMerchantName,
                    merchantCategory = merchantCategory ?: it.merchantCategory,
                    merchantDeliveryTime = merchantDeliveryTime ?: it.merchantDeliveryTime,
                )
            }
        } else if (newMerchantId != _uiState.value.merchantId) {
            return CartError.DifferentMerchant
        }

        // Generate readable options and calculate subtotal
        val readableOptions = generateReadableOptions(item, selectedOptions)
        val subtotal = calculateSubtotal(item, quantity, selectedOptions)

        // Add the item to the cart
        val newItem =
            CartItem(
                id = Clock.System.now().toEpochMilliseconds(),
                quantity = quantity,
                selectedOptions = selectedOptions,
                readableOptions = readableOptions,
                observation = observation,
                subtotal = subtotal,
                item = item,
            )

        _uiState.update { currentState ->
            val updatedItems = currentState.items + newItem
            val totalItems = updatedItems.sumOf { it.quantity }
            val totalPrice = updatedItems.sumOf { it.subtotal }

            currentState.copy(
                items = updatedItems,
                totalItems = totalItems,
                totalPrice = totalPrice,
                merchantDeliveryFee = merchantDeliveryFee ?: currentState.merchantDeliveryFee,
                isOpen = true, // Open the cart when an item is added
            )
        }

        return null
    }

    fun removeItem(itemId: Long) {
        _uiState.update { currentState ->
            val updatedItems = currentState.items.filter { it.id != itemId }
            val totalItems = updatedItems.sumOf { it.quantity }
            val totalPrice = updatedItems.sumOf { it.subtotal }

            currentState.copy(
                items = updatedItems,
                totalItems = totalItems,
                totalPrice = totalPrice,
            )
        }
    }

    fun updateItem(
        itemId: Long,
        quantity: Int,
        selectedOptions: Map<ItemResponse.OptionResponse, Any?>,
        observation: String,
    ) {
        if (quantity <= 0) {
            removeItem(itemId)
            return
        }

        _uiState.update { currentState ->
            val updatedItems =
                currentState.items.map { cartItem ->
                    if (cartItem.id == itemId) {
                        val readableOptions = generateReadableOptions(cartItem.item, selectedOptions)
                        val subtotal = calculateSubtotal(cartItem.item, quantity, selectedOptions)

                        cartItem.copy(
                            quantity = quantity,
                            selectedOptions = selectedOptions,
                            readableOptions = readableOptions,
                            observation = observation,
                            subtotal = subtotal,
                        )
                    } else {
                        cartItem
                    }
                }

            val totalItems = updatedItems.sumOf { it.quantity }
            val totalPrice = updatedItems.sumOf { it.subtotal }

            currentState.copy(
                items = updatedItems,
                totalItems = totalItems,
                totalPrice = totalPrice,
            )
        }
    }

    fun clearCart() {
        _uiState.update {
            CartState()
        }
    }
}

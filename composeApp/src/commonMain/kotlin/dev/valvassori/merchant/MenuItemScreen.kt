package dev.valvassori.merchant

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import dev.valvassori.domain.ItemResponse
import dev.valvassori.ext.formatAsMoney
import dev.valvassori.presentation.merchant.MerchantDetailsViewModel
import org.koin.compose.koinInject

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MenuItemScreen(
    merchantId: String,
    itemId: String,
    onNavigateBack: () -> Unit,
    viewModel: MerchantDetailsViewModel = koinInject()
) {
    var loading by remember { mutableStateOf(true) }
    var error by remember { mutableStateOf<String?>(null) }
    var item by remember { mutableStateOf<ItemResponse?>(null) }
    var merchantName by remember { mutableStateOf("") }
    var merchantDeliveryFee by remember { mutableStateOf(0) }
    var merchantCategory by remember { mutableStateOf("") }
    var merchantDeliveryTime by remember { mutableStateOf(0) }

    var quantity by remember { mutableIntStateOf(1) }

    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(merchantId, itemId) {
        viewModel.loadMerchant(merchantId)
    }

    LaunchedEffect(uiState.merchant) {
        if (uiState.merchant != null) {
            val merchant = uiState.merchant!!
            merchantName = merchant.name
            merchantDeliveryFee = merchant.deliveryFee
            merchantCategory = merchant.category
            merchantDeliveryTime = merchant.deliveryTime

            // Find the item in the merchant's menu
            item = merchant.menu
                .flatMap { it.items.toList() }
                .find { it.id == itemId }

            if (item == null) {
                error = "Item not found"
            }

            loading = false
        } else if (uiState.error != null) {
            error = uiState.error.toString()
            loading = false
        }
    }

    // Remember selected options for each option group
    val selectedOptions = remember(item) {
        mutableStateOf(
            item?.options?.associateWith { option ->
                when (option.type) {
                    ItemResponse.OptionResponse.TypeResponse.Boolean -> null
                    ItemResponse.OptionResponse.TypeResponse.SingleSelection ->
                        option.options.firstOrNull { it.price == 0 }

                    ItemResponse.OptionResponse.TypeResponse.MultipleSelection -> emptySet<ItemResponse.OptionResponse.EntryResponse>()
                    else -> null
                }
            } ?: emptyMap<ItemResponse.OptionResponse, Any?>()
        )
    }

    // Calculate total price based on item price, options, and quantity
    val totalPrice = remember(item, selectedOptions.value, quantity) {
        var price = item?.price ?: 0

        // Add option prices
        selectedOptions.value.forEach { (option, selection) ->
            when (selection) {
                is ItemResponse.OptionResponse.EntryResponse -> price += selection.price
                is Set<*> -> {
                    @Suppress("UNCHECKED_CAST")
                    (selection as Set<ItemResponse.OptionResponse.EntryResponse>).forEach {
                        price += it.price
                    }
                }
            }
        }

        // Multiply by quantity
        price * quantity
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = item?.name ?: "Item Details") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentAlignment = Alignment.Center
        ) {
            when {
                loading -> {
                    CircularProgressIndicator()
                }

                error != null -> {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "Error",
                            style = MaterialTheme.typography.headlineMedium,
                            color = MaterialTheme.colorScheme.error
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = error ?: "Unknown error",
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }
                }

                item == null -> {
                    Text(
                        text = "Item not found",
                        style = MaterialTheme.typography.headlineMedium
                    )
                }

                else -> {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize()
                    ) {
                        // Item header with image
                        item {
                            Column(modifier = Modifier.fillMaxWidth()) {
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(200.dp)
                                ) {
                                    AsyncImage(
                                        model = item!!.imageUrl,
                                        contentDescription = item!!.name,
                                        modifier = Modifier
                                            .fillMaxSize()
                                            .clip(RoundedCornerShape(8.dp)),
                                        contentScale = ContentScale.Crop
                                    )
                                }

                                Column(modifier = Modifier.padding(16.dp)) {
                                    Text(
                                        text = item!!.name,
                                        style = MaterialTheme.typography.headlineMedium,
                                        fontWeight = FontWeight.Bold,
                                        modifier = Modifier.padding(vertical = 8.dp)
                                    )

                                    Text(
                                        text = item!!.description,
                                        style = MaterialTheme.typography.bodyLarge,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant
                                    )

                                    Text(
                                        text = item!!.price.formatAsMoney(),
                                        style = MaterialTheme.typography.titleLarge,
                                        modifier = Modifier.padding(vertical = 8.dp)
                                    )

                                    Divider(modifier = Modifier.padding(vertical = 8.dp))
                                }
                            }
                        }

                        // Options
                        item!!.options.forEach { option ->
                            item {
                                Column(modifier = Modifier.padding(horizontal = 16.dp)) {
                                    Text(
                                        text = option.name,
                                        style = MaterialTheme.typography.titleMedium,
                                        fontWeight = FontWeight.SemiBold,
                                        modifier = Modifier.padding(top = 16.dp, bottom = 4.dp)
                                    )

                                    if (option.description.isNotEmpty()) {
                                        Text(
                                            text = option.description,
                                            style = MaterialTheme.typography.bodyMedium,
                                            color = MaterialTheme.colorScheme.onSurfaceVariant
                                        )
                                    }

                                    Spacer(modifier = Modifier.height(8.dp))
                                }
                            }

                            when (option.type) {
                                ItemResponse.OptionResponse.TypeResponse.Boolean -> {
                                    // Boolean option (yes/no)
                                    items(option.options) { entry ->
                                        OptionItem(
                                            entry = entry,
                                            isSelected = selectedOptions.value[option] == entry,
                                            onSelect = {
                                                val updatedOptions = selectedOptions.value.toMutableMap()
                                                updatedOptions[option] =
                                                    if (selectedOptions.value[option] == entry) null else entry
                                                selectedOptions.value = updatedOptions
                                            },
                                            modifier = Modifier.padding(horizontal = 16.dp)
                                        )
                                    }
                                }

                                ItemResponse.OptionResponse.TypeResponse.SingleSelection -> {
                                    // Single selection (radio buttons)
                                    items(option.options) { entry ->
                                        OptionItem(
                                            entry = entry,
                                            isSelected = selectedOptions.value[option] == entry,
                                            onSelect = {
                                                val updatedOptions = selectedOptions.value.toMutableMap()
                                                updatedOptions[option] = entry
                                                selectedOptions.value = updatedOptions
                                            },
                                            modifier = Modifier.padding(horizontal = 16.dp)
                                        )
                                    }
                                }

                                ItemResponse.OptionResponse.TypeResponse.MultipleSelection -> {
                                    // Multiple selection (checkboxes)
                                    items(option.options) { entry ->
                                        val selectedSet =
                                            selectedOptions.value[option] as? Set<ItemResponse.OptionResponse.EntryResponse>
                                                ?: emptySet()
                                        OptionItem(
                                            entry = entry,
                                            isSelected = selectedSet.contains(entry),
                                            onSelect = {
                                                val updatedOptions = selectedOptions.value.toMutableMap()
                                                val updatedSet = selectedSet.toMutableSet()
                                                if (selectedSet.contains(entry)) {
                                                    updatedSet.remove(entry)
                                                } else {
                                                    updatedSet.add(entry)
                                                }
                                                updatedOptions[option] = updatedSet
                                                selectedOptions.value = updatedOptions
                                            },
                                            modifier = Modifier.padding(horizontal = 16.dp)
                                        )
                                    }
                                }
                            }

                            item {
                                Divider(modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp))
                            }
                        }

                        // Quantity selector
                        item {
                            Column(modifier = Modifier.padding(horizontal = 16.dp)) {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(vertical = 16.dp),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        text = "Quantity",
                                        style = MaterialTheme.typography.titleMedium,
                                        fontWeight = FontWeight.SemiBold
                                    )

                                    Row(
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        IconButton(
                                            onClick = { if (quantity > 1) quantity-- },
                                            enabled = quantity > 1
                                        ) {
                                            RemoveIcon()
                                        }

                                        Text(
                                            text = quantity.toString(),
                                            style = MaterialTheme.typography.titleMedium,
                                            modifier = Modifier.padding(horizontal = 8.dp)
                                        )

                                        IconButton(
                                            onClick = { quantity++ }
                                        ) {
                                            Icon(
                                                imageVector = Icons.Default.Add,
                                                contentDescription = "Increase quantity"
                                            )
                                        }
                                    }
                                }

                                Divider(modifier = Modifier.padding(bottom = 16.dp))
                            }
                        }

                        // Footer with add to cart button
                        item {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 16.dp, vertical = 16.dp)
                            ) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Text(
                                        text = "Total",
                                        style = MaterialTheme.typography.titleLarge
                                    )
                                    Text(
                                        text = totalPrice.formatAsMoney(),
                                        style = MaterialTheme.typography.titleLarge,
                                        fontWeight = FontWeight.Bold
                                    )
                                }

                                Spacer(modifier = Modifier.height(16.dp))

                                Button(
                                    onClick = {
                                        // TODO: Add to cart functionality
                                        onNavigateBack()
                                    },
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    Text(
                                        text = "Add to Cart",
                                        style = MaterialTheme.typography.titleMedium
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun OptionItem(
    entry: ItemResponse.OptionResponse.EntryResponse,
    isSelected: Boolean,
    onSelect: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .clip(MaterialTheme.shapes.small)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            RadioButton(
                selected = isSelected,
                onClick = onSelect
            )

            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 8.dp)
            ) {
                Text(
                    text = entry.name,
                    style = MaterialTheme.typography.bodyLarge
                )

                if (entry.description.isNotEmpty()) {
                    Text(
                        text = entry.description,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            if (entry.price > 0) {
                Text(
                    text = "+" + entry.price.formatAsMoney(),
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(start = 8.dp)
                )
            }
        }
    }
}

@Composable
private fun RemoveIcon() {
    Box(
        modifier = Modifier.size(24.dp),
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .size(12.dp, 2.dp)
                .background(MaterialTheme.colorScheme.onSurface, CircleShape)
        )
    }
}

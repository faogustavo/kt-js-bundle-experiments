package dev.valvassori.presentation.merchant

import dev.valvassori.domain.MerchantResponse
import dev.valvassori.repository.MerchantRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

data class MerchantDetailsState(
    val merchant: MerchantResponse? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)

class MerchantDetailsViewModel : KoinComponent {
    private val repository: MerchantRepository by inject()
    private val coroutineScope = CoroutineScope(Dispatchers.Main)
    
    private val _uiState = MutableStateFlow(MerchantDetailsState(isLoading = true))
    val uiState: StateFlow<MerchantDetailsState> = _uiState.asStateFlow()
    
    fun loadMerchant(merchantId: String) {
        _uiState.update { it.copy(isLoading = true, error = null) }
        
        coroutineScope.launch {
            try {
                val merchant = repository.getMerchantById(merchantId)
                _uiState.update { 
                    it.copy(
                        merchant = merchant,
                        isLoading = false,
                        error = if (merchant == null) "Merchant not found" else null
                    )
                }
            } catch (e: Exception) {
                _uiState.update { 
                    it.copy(
                        isLoading = false,
                        error = e.message ?: "Unknown error"
                    )
                }
            }
        }
    }
} 
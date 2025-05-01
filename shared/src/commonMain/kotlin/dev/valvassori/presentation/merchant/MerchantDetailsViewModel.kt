package dev.valvassori.presentation.merchant

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.valvassori.domain.MerchantResponse
import dev.valvassori.repository.MerchantRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class MerchantDetailsState(
    val merchant: MerchantResponse? = null,
    val isLoading: Boolean = false,
    val error: String? = null,
)

class MerchantDetailsViewModel(
    private val repository: MerchantRepository,
) : ViewModel() {
    private val _uiState = MutableStateFlow(MerchantDetailsState(isLoading = true))
    val uiState: StateFlow<MerchantDetailsState> = _uiState.asStateFlow()

    fun loadMerchant(merchantId: String) {
        _uiState.update { it.copy(isLoading = true, error = null) }

        viewModelScope.launch {
            try {
                val merchant = repository.getMerchantById(merchantId)
                _uiState.update {
                    it.copy(
                        merchant = merchant,
                        isLoading = false,
                        error = if (merchant == null) "Merchant not found" else null,
                    )
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        error = e.message ?: "Unknown error",
                    )
                }
            }
        }
    }
}

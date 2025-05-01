package dev.valvassori.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.valvassori.domain.MerchantResponse
import dev.valvassori.repository.MerchantRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class HomeUiState(
    val isLoading: Boolean = false,
    val merchants: List<MerchantResponse> = emptyList(),
    val error: String? = null,
)

class HomeViewModel(
    private val merchantRepository: MerchantRepository,
) : ViewModel() {
    private val _uiState = MutableStateFlow(HomeUiState(isLoading = true))
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    init {
        loadMerchants()
    }

    private fun loadMerchants() {
        viewModelScope.launch {
            try {
                val merchants = merchantRepository.getAllMerchants()
                _uiState.value =
                    HomeUiState(
                        isLoading = false,
                        merchants = merchants.toList(),
                    )
            } catch (e: Exception) {
                _uiState.value =
                    HomeUiState(
                        isLoading = false,
                        error = e.message ?: "Unknown error occurred",
                    )
            }
        }
    }
}

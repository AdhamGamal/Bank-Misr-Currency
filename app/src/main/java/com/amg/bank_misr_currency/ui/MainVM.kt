package com.amg.bank_misr_currency.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.amg.bank_misr_currency.data.CurrencyRepository
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

class MainVM(private val currencyRepo: CurrencyRepository) : ViewModel() {

    val fromCurrency = mutableStateOf("1")
    val toCurrency = mutableStateOf("1")

    var uiState: UiState by mutableStateOf(UiState.Loading)
        private set

    init {
        getCurrencySymbols()
    }

    private fun getCurrencySymbols() {
        viewModelScope.launch {
            uiState = UiState.Loading
            uiState = try {
                val response = currencyRepo.getCurrencySymbols()
                val symbols = response.data.keys.toList()
                if (symbols.isNotEmpty()) {
                    UiState.Loaded(symbols)
                } else {
                    UiState.Error("")
                }
            } catch (e: IOException) {
                UiState.Error(e.message.toString())
            } catch (e: HttpException) {
                UiState.Error(e.message.toString())
            }
        }
    }


    private fun getConversionRate(from: String, to: String) {
        viewModelScope.launch {
            try {
                val response = currencyRepo.getConversionRate(from, to)
                val rate = response.data.values.firstOrNull() ?: 0.0
                toCurrency.value = (fromCurrency.value.toInt() * rate).toString()
            } catch (_: IOException) {

            } catch (_: HttpException) {

            }
        }
    }
}

sealed interface UiState {
    data class Loaded(val symbols: List<String>) : UiState
    data class Error(val error: String) : UiState
    object Loading : UiState
}
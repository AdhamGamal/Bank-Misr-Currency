package com.amg.bank_misr_currency.data

import com.amg.bank_misr_currency.model.ConversionRateResponse
import com.amg.bank_misr_currency.model.CurrencySymbolsResponse
import com.amg.bank_misr_currency.network.FixerApiService

interface CurrencyRepository {
    suspend fun getCurrencySymbols(): CurrencySymbolsResponse
    suspend fun getConversionRate(
        fromCurrency: String,
        toCurrency: String
    ): ConversionRateResponse
}

class NetworkCurrencyRepository(
    private val fixerApiService: FixerApiService
) : CurrencyRepository {
    override suspend fun getCurrencySymbols(): CurrencySymbolsResponse =
        fixerApiService.getCurrencySymbols()

    override suspend fun getConversionRate(
        fromCurrency: String,
        toCurrency: String
    ): ConversionRateResponse = fixerApiService.getConversionRate(fromCurrency, toCurrency)
}

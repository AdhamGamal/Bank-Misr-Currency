package com.amg.bank_misr_currency.data

import com.amg.bank_misr_currency.model.CurrencySymbolsResponse
import com.amg.bank_misr_currency.network.FixerApiService
import okhttp3.ResponseBody

interface CurrencyRepository {
    suspend fun getCurrencySymbols(): CurrencySymbolsResponse
//    suspend fun getConversionRate(): CurrencySymbolsResponse
}

class NetworkCurrencyRepository(
    private val fixerApiService: FixerApiService
) : CurrencyRepository {
    override suspend fun getCurrencySymbols(): CurrencySymbolsResponse = fixerApiService.getCurrencySymbols()
}

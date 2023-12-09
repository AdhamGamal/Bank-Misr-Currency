package com.amg.bank_misr_currency.network

import com.amg.bank_misr_currency.model.ConversionRateResponse
import com.amg.bank_misr_currency.model.CurrencySymbolsResponse
import retrofit2.http.GET
import retrofit2.http.Query


interface FixerApiService {

    @GET("/v1/currencies")
    suspend fun getCurrencySymbols(
        @Query("apikey") apiKey: String = "fca_live_mLB4dbrfQzhoQ3RA0coyCRaTyMxPnn4LrnuZqqBH"
    ): CurrencySymbolsResponse

    @GET("/v1/latest")
    suspend fun getConversionRate(
        @Query("base_currency") fromCurrency: String,
        @Query("currencies") toCurrency: String,
        @Query("access_key") accessKey: String = "fca_live_mLB4dbrfQzhoQ3RA0coyCRaTyMxPnn4LrnuZqqBH"
    ): ConversionRateResponse
}

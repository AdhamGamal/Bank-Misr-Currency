package com.amg.bank_misr_currency.model

import kotlinx.serialization.Serializable

@Serializable
data class CurrencySymbolsResponse(
    val data: Map<String, CurrencyDetail> = mapOf(),
)

@Serializable
data class CurrencyDetail(
    val symbol: String,
    val name: String,
    val code: String,
)

@Serializable
data class ConversionRateResponse(
    val data: Map<String, Double> = mapOf(),
)

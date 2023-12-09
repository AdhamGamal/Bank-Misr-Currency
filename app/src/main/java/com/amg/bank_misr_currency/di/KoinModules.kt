package com.amg.bank_misr_currency.di

import com.amg.bank_misr_currency.data.CurrencyRepository
import com.amg.bank_misr_currency.data.NetworkCurrencyRepository
import com.amg.bank_misr_currency.network.FixerApiService
import com.amg.bank_misr_currency.ui.MainVM
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module
import retrofit2.Retrofit

val appModule: Module = module {

    viewModel { MainVM(get()) }
}

private val json = Json {
    ignoreUnknownKeys = true
}

val networkModule: Module = module {

    single<CurrencyRepository> {

        val baseUrl = "https://api.freecurrencyapi.com/"

        val retrofit: Retrofit = Retrofit.Builder()
            .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
            .baseUrl(baseUrl)
            .build()

        val retrofitService = retrofit.create(FixerApiService::class.java)

        NetworkCurrencyRepository(retrofitService)
    }
}
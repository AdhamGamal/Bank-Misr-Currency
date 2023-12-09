package com.amg.bank_misr_currency.network

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import com.amg.bank_misr_currency.model.CurrencySymbolsResponse
import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Date


interface FixerApiService {

    @GET("/v1/currencies")
    suspend fun getCurrencySymbols(
        @Query("apikey") apiKey: String = "fca_live_mLB4dbrfQzhoQ3RA0coyCRaTyMxPnn4LrnuZqqBH"
    ): CurrencySymbolsResponse

//    @GET("/api/api/symbols/{date}")
//    suspend fun getConversionRate(
//        @Path("date") date: String = "2023-12-01",
//        @Query("access_key") accessKey: String = "45fb3a7aa1286ce8446620beac6c76a7"
//    ): CurrencySymbolsResponse
}

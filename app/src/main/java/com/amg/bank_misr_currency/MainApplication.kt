package com.amg.bank_misr_currency

import android.app.Application
import com.amg.bank_misr_currency.di.appModule
import com.amg.bank_misr_currency.di.networkModule
import org.koin.core.context.startKoin

class MainApplication : Application() {

    private val modules = listOf(
        appModule, networkModule
    )

    override fun onCreate() {
        super.onCreate()

        startKoin() {
            modules(modules)
        }
    }
}


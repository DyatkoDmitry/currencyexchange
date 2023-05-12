package com.example.currency_exchange

import android.app.Application
import android.util.Log
import com.example.currency_exchange.modules.ModuleContext
import com.example.currency_exchange.modules.ModuleResources

class App: Application() {
    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()

        appComponent = DaggerAppComponent
            .builder()
            .moduleContext(ModuleContext(this))
            .build()
    }

}
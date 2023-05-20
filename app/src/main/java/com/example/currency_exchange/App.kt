package com.example.currency_exchange

import android.app.Application
import com.example.currency_exchange.modules.ModuleContext

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
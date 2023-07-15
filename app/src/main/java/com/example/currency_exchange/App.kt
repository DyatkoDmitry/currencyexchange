package com.example.currency_exchange

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

@HiltAndroidApp
class App: Application() {

    @ApplicationContext
    val context = this

    @Inject
    lateinit var viewModelFactory:ViewModelFactory

}

/* lateinit var appComponent: AppComponent

   override fun onCreate() {
       super.onCreate()

       appComponent = DaggerAppComponent
           .builder()
           .moduleContext(ModuleContext(this))
           .build()
   }*/
package com.example.currency_exchange


import com.example.currency_exchange.model.APIService
import com.example.currency_exchange.model.AdapterFactory
import com.example.currency_exchange.model.LocalState
import com.example.currency_exchange.model.LocalStateService
import com.example.currency_exchange.modules.ModuleAdapter
import com.example.currency_exchange.modules.ModuleRetrofit
import com.example.currency_exchange.modules.ModuleStates
import dagger.Component
import javax.inject.Inject
import javax.inject.Scope

@AppScope
@Component(modules = [ModuleStates::class, ModuleRetrofit::class, ModuleAdapter::class])
interface AppComponent {

    fun getLocalStateService(): LocalStateService

    //fun getLocalStates(): List<LocalState>

    fun getAPIService(): APIService

    fun getViewModelsFactory(): ViewModelFactory

    fun injectMainActivity(MainActivity: MainActivity)

}

@Scope
@Retention(AnnotationRetention.RUNTIME)
annotation class AppScope
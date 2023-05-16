package com.example.currency_exchange


import com.example.currency_exchange.model.APIService
import com.example.currency_exchange.model.ItemStateService
import com.example.currency_exchange.modules.ModuleAdapter
import com.example.currency_exchange.modules.ModuleRetrofit
import com.example.currency_exchange.modules.ModuleStates
import dagger.Component

@Component(modules = [ModuleStates::class, ModuleRetrofit::class, ModuleAdapter::class])
interface AppComponent {

    fun getItemStateService(): ItemStateService

    fun injectViewModdel(viewModel: ViewModelMy)

    fun getAdapter(): Adapter

    fun getAPIService(): APIService

    fun getViewModelsFactory(): ViewModelFactory

}
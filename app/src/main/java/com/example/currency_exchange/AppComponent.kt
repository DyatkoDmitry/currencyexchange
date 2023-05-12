package com.example.currency_exchange


import com.example.currency_exchange.model.ResourceProvider
import com.example.currency_exchange.modules.EmptyClass
import com.example.currency_exchange.modules.ModuleContext
import com.example.currency_exchange.modules.ModuleEmpty
import com.example.currency_exchange.modules.ModuleRecycler
import com.example.currency_exchange.modules.ModuleResources
import com.example.currency_exchange.modules.ModuleRetrofit
import com.example.currency_exchange.view.RecyclerAdapter
import dagger.Component

@Component(modules = [ModuleResources::class, ModuleEmpty::class, ModuleRetrofit::class, ModuleRecycler::class])
interface AppComponent {

    fun getResourceProvider(): ResourceProvider

    fun injectViewModdel(viewModelMy: ViewModelMy)

    fun injectAdapter(recyclerAdapter: RecyclerAdapter)

    fun getRecyclerAdapter(): RecyclerAdapter

}
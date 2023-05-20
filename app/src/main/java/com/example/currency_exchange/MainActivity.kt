package com.example.currency_exchange

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.example.currency_exchange.databinding.ActivityMainBinding
import com.example.currency_exchange.model.AdapterFactory
import com.example.currency_exchange.model.AdapterFactory_Impl
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val viewModel: ViewModelMy by viewModels{(application as App).appComponent.getViewModelsFactory()}

    @Inject
    lateinit var adapterFactory: AdapterFactory

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        (application as App).appComponent.injectMainActivity(this)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.listRates.observe(this, Observer {
            val adapter = adapterFactory.create(viewModel.listRates.value!!)
            val recyclerView: RecyclerView = binding.recyclerView
            recyclerView.addItemDecoration(DividerItemDecoration(this,DividerItemDecoration.VERTICAL))
            recyclerView.adapter = adapter
        })

        viewModel.listItemStates.observe(this, Observer {
            for (i in it.indices){
                Log.d("TAG", "Item: " + it.get(i).base.toString())
            }
        })
    }
}
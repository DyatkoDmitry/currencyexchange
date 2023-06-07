package com.example.currency_exchange

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.currency_exchange.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val viewModel: ViewModelMy by viewModels{(application as App).appComponent.getViewModelsFactory()}
    private lateinit var adapter:Adapter2
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        (application as App).appComponent.injectMainActivity(this)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val recyclerView: RecyclerView = binding.recyclerView
        recyclerView.addItemDecoration(DividerItemDecoration(recyclerView.context,DividerItemDecoration.VERTICAL))

        lifecycleScope.launch {

            val items = viewModel.getItems()
            adapter = Adapter2(items, viewModel.itemInputListener, viewModel.itemFocusListener, applicationContext)
            recyclerView.adapter = adapter

            adapter.sharedFlowEditable.collect(){
                //Log.d("TAG", "in sharedFlow = ${it.toString()}")
            }

            /*adapter.flowEditable.collect(){
                Log.d("TAG", "in float = ${it.toString()}")
            }*/
        }


        viewModel.currentListItems.observe(this, Observer {
            adapter.setNewListItems(it)
            recyclerView.scrollToPosition(0)
        })

    }
}

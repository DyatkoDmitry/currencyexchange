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
import kotlinx.coroutines.withContext


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

            //viewModel.setInitializedLists()

            //val viewItems = viewModel.getViewItems()
            val items = viewModel.getItems()
            adapter = Adapter2(items, viewModel.itemFocusListener)


            recyclerView.adapter = adapter


            adapter.sharedFlowEditable.collect(){
                viewModel.setCoefficient(it)
            }
        }

        lifecycleScope.launch {
            viewModel.startRefreshingRates()
        }

        viewModel.currentListItems.observe(this, Observer {

            adapter.setNewListItems(it)
            adapter.notifyDataSetChanged()
            recyclerView.scrollToPosition(0)
        })
    }
}

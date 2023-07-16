package com.example.currency_exchange

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.example.currency_exchange.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: ViewModelMy by viewModels()
    private lateinit var adapter:Adapter

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val recyclerView: RecyclerView = binding.recyclerView
        recyclerView.addItemDecoration(DividerItemDecoration(recyclerView.context,DividerItemDecoration.VERTICAL))

        lifecycleScope.launch {

            val items = viewModel.getItems()
            adapter = Adapter(items, viewModel.itemFocusListener)

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

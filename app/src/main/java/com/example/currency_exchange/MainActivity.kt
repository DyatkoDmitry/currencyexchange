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
import com.example.currency_exchange.view.RecyclerAdapter
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val viewModel: ViewModelMy by viewModels()
    private lateinit var recyclerAdapter: RecyclerView.Adapter<RecyclerAdapter.MyViewHolder>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        (application as App).appComponent.injectViewModdel(viewModel)

        recyclerAdapter = (application as App).appComponent.getRecyclerAdapter()

        val recyclerView: RecyclerView = binding.recyclerView
        recyclerView.addItemDecoration(DividerItemDecoration(this,DividerItemDecoration.VERTICAL))
        recyclerView.adapter = recyclerAdapter

        viewModel.getCertainCurrency("AUD")

        viewModel.liveDataCurrency.observe(this, Observer {
            Log.d("TAG", "One base in MainActivity = ${it.base}")
        })

        viewModel.getAllDataCurrency()

        lifecycleScope.launch {
            while(true){
                delay(1000)
                Log.d("TAG"," tick ")
            }
        }
    }
}
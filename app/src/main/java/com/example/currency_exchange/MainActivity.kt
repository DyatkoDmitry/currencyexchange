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
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val viewModel: ViewModelMy by viewModels()
    private lateinit var adapter: RecyclerView.Adapter<Adapter.MyViewHolder>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        (application as App).appComponent.injectViewModdel(viewModel)

        adapter = (application as App).appComponent.getAdapter()

        //val apiService = (application as App).appComponent.getAPIService()

        val recyclerView: RecyclerView = binding.recyclerView
        recyclerView.addItemDecoration(DividerItemDecoration(this,DividerItemDecoration.VERTICAL))
        recyclerView.adapter = adapter

       /* lifecycleScope.launch {
            while(true){
                delay(1000)
                Log.d("TAG"," tick ")
            }
        }*/

        viewModel.listRates.observe(this, Observer {
            for(currency in it){
                Log.d("TAG", it.size.toString())
            }
        })

    }
}
package com.example.currency_exchange

import android.os.Bundle
import androidx.activity.viewModels
import androidx.annotation.DrawableRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.example.currency_exchange.databinding.ActivityMainBinding
import com.example.currency_exchange.model.AdapterFactory
import com.example.currency_exchange.model.ItemService
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val viewModel: ViewModelMy by viewModels{(application as App).appComponent.getViewModelsFactory()}

   /* @Inject
    lateinit var adapterFactory: AdapterFactory*/

/*    @Inject
    lateinit var itemService: ItemService*/

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        (application as App).appComponent.injectMainActivity(this)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val recyclerView: RecyclerView = binding.recyclerView
        recyclerView.addItemDecoration(DividerItemDecoration(recyclerView.context,DividerItemDecoration.VERTICAL))



        viewModel.listItems.observe(this, Observer {

            val adapter2 = Adapter2(it.toMutableList())
            adapter2.setListItems(it.toMutableList())
            recyclerView.adapter = adapter2

         })

    }
}

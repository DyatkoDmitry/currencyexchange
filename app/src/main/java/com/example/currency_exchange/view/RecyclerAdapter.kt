package com.example.currency_exchange.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.currency_exchange.R
import com.example.currency_exchange.model.Cell
import com.example.currency_exchange.model.CurrencyAPIService
import com.example.currency_exchange.model.ResourceProvider
import javax.inject.Inject


class RecyclerAdapter(val currencyAPIService: CurrencyAPIService, val resourceProvider: ResourceProvider):RecyclerView.Adapter<RecyclerAdapter.MyViewHolder>() {

    /*@Inject lateinit var currencyAPIService: CurrencyAPIService
    @Inject lateinit var resourceProvider: ResourceProvider*/

    class MyViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        val imageView: ImageView = itemView.findViewById(R.id.imageView)
        val textViewBase: TextView = itemView.findViewById(R.id.textViewBase)
        val textViewDescription: TextView = itemView.findViewById(R.id.textViewDescription)
        val editText: EditText = itemView.findViewById(R.id.editText)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.recycler_view_item, parent, false)
        return MyViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return resourceProvider.getListCells().size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val cell = resourceProvider.getListCells().get(position)
        holder.imageView.setImageDrawable(cell.drawable)
        holder.textViewBase.text = cell.base
        holder.textViewDescription.text = cell.name
    }

    fun update(){
        notifyDataSetChanged()
    }
}
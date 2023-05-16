package com.example.currency_exchange

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.currency_exchange.model.APIService
import com.example.currency_exchange.model.ItemState
import com.example.currency_exchange.model.ItemStateService
import com.example.currency_exchange.model.Rate

class Adapter(val listItemStates: List<ItemState>): RecyclerView.Adapter<Adapter.MyViewHolder>() {

    lateinit var rates: List<Rate>

    class MyViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        val imageView: ImageView = itemView.findViewById(R.id.imageView)
        val textViewBase: TextView = itemView.findViewById(R.id.textViewBase)
        val textViewDescription: TextView = itemView.findViewById(R.id.textViewDescription)
        val editText: EditText = itemView.findViewById(R.id.editText)
    }

    override fun onCreateViewHolder(parent: ViewGroup,viewType: Int): Adapter.MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.recycler_view_item, parent, false)
        return Adapter.MyViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return listItemStates.size
    }

    override fun onBindViewHolder(holder: Adapter.MyViewHolder, position: Int) {
        val itemState = listItemStates.get(position)
        holder.imageView.setImageDrawable(itemState.drawable)
        holder.textViewBase.text = itemState.base
        holder.textViewDescription.text = itemState.name

    }

    fun insertRates(listRates:List<Rate>){
        rates = listRates
        notifyDataSetChanged()
    }
}
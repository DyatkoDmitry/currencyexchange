package com.example.currency_exchange

import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.currency_exchange.model.LocalState
import com.example.currency_exchange.model.RemoteState
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject

class Adapter @AssistedInject constructor(val listItemStates: List<LocalState>, @Assisted val listRemoteStates: List<RemoteState>): RecyclerView.Adapter<Adapter.MyViewHolder>() {

    class MyViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        val imageView: ImageView = itemView.findViewById(R.id.imageView)
        val textViewBase: TextView = itemView.findViewById(R.id.textViewBase)
        val textViewDescription: TextView = itemView.findViewById(R.id.textViewDescription)
        val editText: EditText = itemView.findViewById(R.id.editText)
    }

    override fun onCreateViewHolder(parent: ViewGroup,viewType: Int): Adapter.MyViewHolder {

        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.recycler_view_item, parent, false)

        val myViewHolder:MyViewHolder = MyViewHolder(itemView)

        itemView.findViewById<EditText>(R.id.editText).addTextChangedListener(
            object: TextWatcher {
                override fun afterTextChanged(editable: Editable?) {
                    editable?.toString()?.let {

                        val position = myViewHolder.adapterPosition

                        if (position != RecyclerView.NO_POSITION) {
                            position
                        }
                    }
                }
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                }
            }
        )

        return myViewHolder
    }

    override fun getItemCount(): Int {
        return listItemStates.size
    }

    override fun onBindViewHolder(holder: Adapter.MyViewHolder, position: Int) {
        val itemState = listItemStates.get(position)
        holder.imageView.setImageDrawable(itemState.drawable)
        holder.textViewBase.text = itemState.base
        holder.textViewDescription.text = itemState.name
        holder.editText.setText(getRateByItem(itemState))
    }

    fun getRateByItem(localState: LocalState): String{

        var currencyRate = ""

        for (rateItem in listRemoteStates){
            if(localState.base.equals(rateItem.base)){
                currencyRate = rateItem.rate.toString()
            }
        }
        return currencyRate
    }
}


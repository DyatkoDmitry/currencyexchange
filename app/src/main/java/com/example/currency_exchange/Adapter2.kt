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
import com.example.currency_exchange.model.Item
import com.example.currency_exchange.model.LocalState
import com.example.currency_exchange.model.RemoteState
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import javax.inject.Inject


class Adapter2 @Inject constructor(listItems:MutableList<Item>): RecyclerView.Adapter<Adapter2.MyViewHolder>() {

    val listItems:MutableList<Item> = listItems

    fun setListItems(newListItems: List<Item>){
        listItems.clear()
        listItems.addAll(0, newListItems)
        notifyDataSetChanged()
    }

    class MyViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        val imageView: ImageView = itemView.findViewById(R.id.imageView)
        val textViewBase: TextView = itemView.findViewById(R.id.textViewBase)
        val textViewDescription: TextView = itemView.findViewById(R.id.textViewDescription)
        val editText: EditText = itemView.findViewById(R.id.editText)
    }

    override fun onCreateViewHolder(parent: ViewGroup,viewType: Int): Adapter2.MyViewHolder {

        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.recycler_view_item, parent, false)

        val myViewHolder:MyViewHolder = MyViewHolder(itemView)

        itemView.findViewById<EditText>(R.id.editText).addTextChangedListener(
            object: TextWatcher {
                override fun afterTextChanged(editable: Editable?) {
                    editable?.toString()?.let {
                        Log.d("TAG", it)
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
        return listItems.size
    }

    override fun onBindViewHolder(holder: Adapter2.MyViewHolder, position: Int) {
        val itemState = listItems.get(position)
        holder.imageView.setImageDrawable(itemState.drawable)
        holder.textViewBase.text = itemState.base
        holder.textViewDescription.text = itemState.name
        holder.editText.setText(itemState.rate.toString())
    }
}


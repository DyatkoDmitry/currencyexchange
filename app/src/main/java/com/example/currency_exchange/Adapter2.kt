package com.example.currency_exchange


import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.core.widget.doAfterTextChanged
import androidx.recyclerview.widget.RecyclerView
import com.example.currency_exchange.model.Item
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Scope

typealias ItemInputListener = (Float?) -> Unit
typealias ItemFocusListener = (Int) -> Unit

class Adapter2 @Inject constructor(var listItems:List<Item>, val itemInputListener: ItemInputListener, val itemFocusListener: ItemFocusListener, context: Context): RecyclerView.Adapter<Adapter2.MyViewHolder>() {

    private val _sharedFlowEditable = MutableSharedFlow<Float>()
    val sharedFlowEditable = _sharedFlowEditable.asSharedFlow()

    private var setedListener = false
    lateinit var jobTextListener: Job

    fun setNewListItems(newListItems:List<Item>){
        listItems = newListItems
        notifyDataSetChanged()
    }

    class MyViewHolder(itemView: View, val textWatcher: TextWatcher): RecyclerView.ViewHolder(itemView){

        val imageView: ImageView = itemView.findViewById(R.id.imageView)
        val textViewBase: TextView = itemView.findViewById(R.id.textViewBase)
        val textViewDescription: TextView = itemView.findViewById(R.id.textViewDescription)
        val editText: EditText = itemView.findViewById(R.id.editText)

        fun enableTextWatcher() {
            editText.addTextChangedListener(textWatcher)
        }

        fun disableTextWatcher() {
            editText.removeTextChangedListener(textWatcher)
        }
    }

    override fun onViewAttachedToWindow(holder: MyViewHolder) {
        super.onViewAttachedToWindow(holder)
        holder.enableTextWatcher()


        holder.editText.setOnFocusChangeListener { view, b ->
            if((b)&&(holder.adapterPosition != 0)){
                Log.d("TAG", " in setOnFocusChangeListener: ${holder.adapterPosition.toString()}")
                selectItem(holder.adapterPosition)
            }
        }

    }

    override fun onViewDetachedFromWindow(holder: MyViewHolder) {
        super.onViewDetachedFromWindow(holder)
           holder.disableTextWatcher()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.recycler_view_item, parent, false)

        val myViewHolder = MyViewHolder(itemView, textWatcherMy)


        itemView.setOnTouchListener { view, motionEvent ->

            if (myViewHolder.adapterPosition == RecyclerView.NO_POSITION) {
                return@setOnTouchListener true
            }

            val positionClicked = when(motionEvent.action){
                MotionEvent.ACTION_DOWN -> myViewHolder.adapterPosition
                else -> null
            }

            if(positionClicked != -1 && positionClicked != null){
                Log.d("TAG", " in setOnTouchListener: ${myViewHolder.adapterPosition.toString()}")

                selectItem(myViewHolder.adapterPosition)
            }

            return@setOnTouchListener true
        }

        myViewHolder.editText.setOnClickListener {
            Log.d("TAG", " In setOnClickListener")
        }
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
        //holder.editText.clearFocus()
        if(position == 0){
            holder.editText.requestFocus()
        }

       /* holder.editText.setOnFocusChangeListener { view, b ->
            if(b){
                Log.d("TAG", " in setOnFocusChangeListener: ${holder.adapterPosition.toString()}")
                selectItem(holder.adapterPosition)
            }
        }*/
    }

    private fun selectItem(position:Int){
        Log.d("TAG", " in selectItem: ${position.toString()}")
        itemFocusListener.invoke(position)
    }

    private val textWatcherMy = object : TextWatcher {

        val scope = CoroutineScope(Dispatchers.Main + Job())

        override fun afterTextChanged(editable: Editable?) {
        }

        override fun beforeTextChanged(s: CharSequence?,start: Int,count: Int,after: Int) {
        }

        override fun onTextChanged(s: CharSequence?,start: Int,before: Int,count: Int) {

            jobTextListener = scope.launch {

                    Log.d("TAG", "In text listeber: ${s}")
                    _sharedFlowEditable.emit(s.toString().toFloat())
            }
        }
    }
}







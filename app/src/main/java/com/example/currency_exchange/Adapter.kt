package com.example.currency_exchange


import DecimalDigitsInputFilter
import android.text.Editable
import android.text.InputFilter
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.currency_exchange.model.Item
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


typealias ItemFocusListener = (Int) -> Unit

class Adapter @Inject constructor(viewListItems:MutableList<Item>, val itemFocusListener: ItemFocusListener): RecyclerView.Adapter<Adapter.MyViewHolder>() {

    var viewListItems:MutableList<Item> = viewListItems
    private val _sharedFlowEditable = MutableSharedFlow<Float>()
    val sharedFlowEditable = _sharedFlowEditable.asSharedFlow()

    lateinit var jobTextListener: Job

    fun setNewListItems(newListItems:MutableList<Item>){
        viewListItems = newListItems
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
                  selectItem(myViewHolder.adapterPosition)
            }

            return@setOnTouchListener true
        }

        myViewHolder.editText.setOnFocusChangeListener { view, hasFocus ->
            if((hasFocus)&&(myViewHolder.adapterPosition != 0)){
                  selectItem(myViewHolder.adapterPosition)
            }
        }



        return myViewHolder
    }

    override fun getItemCount(): Int {
        return viewListItems.size
    }

    override fun onBindViewHolder(holder: Adapter.MyViewHolder, position: Int) {
        val itemState = viewListItems.get(position)

        holder.imageView.setImageDrawable(itemState.drawable)
        holder.textViewBase.text = itemState.base
        holder.textViewDescription.text = itemState.name

        holder.editText.setFilters(arrayOf<InputFilter>(InputFilterEditText()))

        holder.editText.setText(itemState.rate.toString())
    }

    private fun selectItem(position:Int){
        itemFocusListener.invoke(position)
    }

    private val textWatcherMy = object : TextWatcher {

        val scope = CoroutineScope(Dispatchers.Main + Job())

        override fun afterTextChanged(editable: Editable?) {}

        override fun beforeTextChanged(s: CharSequence?,start: Int,count: Int,after: Int) {}

        override fun onTextChanged(s: CharSequence?,start: Int,before: Int,count: Int) {

            jobTextListener = scope.launch {
                _sharedFlowEditable.emit(s.toString().toFloat())
            }
        }
    }
}







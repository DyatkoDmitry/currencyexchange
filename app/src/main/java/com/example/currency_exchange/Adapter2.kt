package com.example.currency_exchange


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
import androidx.recyclerview.widget.RecyclerView
import com.example.currency_exchange.model.Item
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

typealias ItemInputListener = (Float?) -> Unit
typealias ItemFocusListener = (Int) -> Unit

class Adapter2 @Inject constructor(var listItems:List<Item>, val itemInputListener: ItemInputListener, val itemFocusListener: ItemFocusListener): RecyclerView.Adapter<Adapter2.MyViewHolder>() {

    private var positionCurrent: Int = 0

    private val _sharedFlowEditable = MutableSharedFlow<Float>()
    val sharedFlowEditable = _sharedFlowEditable.asSharedFlow()

    private var setedListener = false
    val tWatcher = TextWatcherEditText(_sharedFlowEditable)

    val scope = CoroutineScope(Dispatchers.Default + Job())

    fun setNewListItems(newListItems:List<Item>){
        listItems = newListItems
        notifyDataSetChanged()
    }

    class MyViewHolder(itemView: View, val textWatcherEditText: TextWatcherEditText ): RecyclerView.ViewHolder(itemView){


        val imageView: ImageView = itemView.findViewById(R.id.imageView)
        val textViewBase: TextView = itemView.findViewById(R.id.textViewBase)
        val textViewDescription: TextView = itemView.findViewById(R.id.textViewDescription)
        val editText: EditText = itemView.findViewById(R.id.editText)

        fun enableTextWatcher() {
            editText.addTextChangedListener(textWatcherEditText)
        }

        fun disableTextWatcher() {
            editText.removeTextChangedListener(textWatcherEditText)
        }
    }

    /*class ViewHolder(v: View, myCustomEditTextListener: MyCustomEditTextListener) :
        RecyclerView.ViewHolder(v) {
        // each data item is just a string in this case
        var mEditText: EditText
        var myCustomEditTextListener: MyCustomEditTextListener

        init {
            mEditText = v.findViewById<View>(R.id.editText) as EditText
            this.myCustomEditTextListener = myCustomEditTextListener
        }

        fun enableTextWatcher() {
            mEditText.addTextChangedListener(myCustomEditTextListener)
        }

        fun disableTextWatcher() {
            mEditText.removeTextChangedListener(myCustomEditTextListener)
        }
    }*/


    override fun onViewRecycled(holder: MyViewHolder) {
        super.onViewRecycled(holder)
        if((holder.adapterPosition == 0) &&(holder.editText.getTag() == 1)){
            Log.d("TAG", "tag = ${(holder.editText.getTag())} and et = ${holder.editText.text}")
        }
    }

    override fun onViewAttachedToWindow(holder: MyViewHolder) {
        super.onViewAttachedToWindow(holder)

        if((holder.adapterPosition == 0) &&(holder.editText.getTag() == 1)){
            //holder.editText.addTextChangedListener(TextWatcherEditText(_sharedFlowEditable))
            holder.enableTextWatcher()
        }
    }

    override fun onViewDetachedFromWindow(holder: MyViewHolder) {
        super.onViewDetachedFromWindow(holder)


        if((holder.adapterPosition == 0) &&(holder.editText.getTag() != 1)){
            //holder.editText.removeTextChangedListener(TextWatcherEditText(_sharedFlowEditable))
            holder.disableTextWatcher()
        }
       /* holder.editText.tag?.let {

            Log.d("TAG", "tag = ${(holder.editText.getTag(1))} and et = ${holder.editText.text}")

            holder.editText.removeTextChangedListener(TextWatcherEditText(_sharedFlowEditable))

        }*/


        /*if(holder.adapterPosition != 0){
            holder.editText.removeTextChangedListener(TextWatcherEditText(_sharedFlowEditable))
        }*/

        //Log.d("TAG", "onViewDetachedFromWindow position = ${holder.adapterPosition}")
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.recycler_view_item, parent, false)

        val myViewHolder = MyViewHolder(itemView, tWatcher)

        itemView.setOnClickListener {

            Log.d("TAG", "click listener = ${myViewHolder.adapterPosition}")
            positionCurrent = myViewHolder.adapterPosition
            itemFocusListener.invoke(myViewHolder.adapterPosition)
        }

        itemView.findViewById<EditText>(R.id.editText).setOnTouchListener { view, motionEvent ->

            if (myViewHolder.adapterPosition == RecyclerView.NO_POSITION) {
                return@setOnTouchListener true
            }

            val positionClicked = when(motionEvent.action){
                MotionEvent.ACTION_DOWN -> myViewHolder.adapterPosition
                else -> null
            }

            if(positionClicked != -1 && positionClicked != null){
                positionCurrent = myViewHolder.adapterPosition
                itemFocusListener.invoke(positionCurrent)
            }
            Log.d("TAG", "click listener editText = ${myViewHolder.adapterPosition}")
            return@setOnTouchListener true
        }


        //itemView.findViewById<EditText>(R.id.editText).addTextChangedListener(TextWatcherEditText(_sharedFlowEditable))

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

        if(position == 0){
            holder.editText.requestFocus()
            //holder.editText.setTag(1)
        }

        if((holder.adapterPosition == 0)&&(setedListener == false)){
            Log.d("TAG", "seted listener")
            setedListener = true
            holder.editText.setTag(1)
            holder.editText.addTextChangedListener(TextWatcherEditText(_sharedFlowEditable))
        }
    }

}

class TextWatcherEditText(val _sharedFlowEditable: MutableSharedFlow<Float>): TextWatcher {

    val scope = CoroutineScope(Dispatchers.Main + Job())

    override fun afterTextChanged(editable: Editable?) {
        editable?.toString()?.let {

            scope.launch {
                Log.d("TAG", "In scope: ${it.toString()}")
                _sharedFlowEditable.emit(it.toFloat())
            }
        }
    }

    override fun beforeTextChanged(s: CharSequence?,start: Int,count: Int,after: Int) {
    }
    override fun onTextChanged(s: CharSequence?,start: Int,before: Int,count: Int) {
    }
}






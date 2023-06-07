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

    private var positionCurrent: Int = 0

    private val _sharedFlowEditable = MutableSharedFlow<Float>()
    val sharedFlowEditable = _sharedFlowEditable.asSharedFlow()

    private var setedListener = false
    lateinit var jobTextListener: Job
    var isGetting = true

    val scope = CoroutineScope(Dispatchers.Default + Job())

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
            //Log.d("TAG", "enabled")
            editText.addTextChangedListener(textWatcher)
        }

        fun disableTextWatcher() {
            //Log.d("TAG", "disable")
            editText.removeTextChangedListener(textWatcher)
        }
    }

    override fun onViewAttachedToWindow(holder: MyViewHolder) {
        super.onViewAttachedToWindow(holder)
        holder.enableTextWatcher()
        isGetting = true

        if((holder.adapterPosition == 0) &&(holder.editText.getTag() == 1)){
            //holder.editText.addTextChangedListener(TextWatcherEditText(_sharedFlowEditable))
            holder.enableTextWatcher()
            isGetting = true
        }

        holder.editText.setOnFocusChangeListener { view, b ->
            if(b){
                Log.d("TAG", "Edit in focus = ${(view as EditText).text.toString()}")
                Log.d("TAG", "Pos in focus = ${holder.adapterPosition.toString()}")
            }

        }

        //Log.d("TAG", "onViewAttachedToWindow position = ${holder.adapterPosition}")

        /*if((holder.adapterPosition == 0) &&(holder.editText.getTag() == 1)){
            //holder.editText.addTextChangedListener(TextWatcherEditText(_sharedFlowEditable))
            holder.enableTextWatcher()
            isGetting = true
        }*/
    }

    override fun onViewDetachedFromWindow(holder: MyViewHolder) {
        super.onViewDetachedFromWindow(holder)

        if((holder.adapterPosition == 0) &&(holder.editText.getTag() == 1)){
           isGetting = false
           holder.disableTextWatcher()
        }

        //Log.d("TAG", "onViewDetachedFromWindow position = ${holder.adapterPosition}")

       /* holder.editText.tag?.let {

            Log.d("TAG", "tag = ${(holder.editText.getTag(1))} and et = ${holder.editText.text}")

            holder.editText.removeTextChangedListener(TextWatcherEditText(_sharedFlowEditable))

        }*/

        //Log.d("TAG", "onViewDetachedFromWindow position = ${holder.adapterPosition}")
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.recycler_view_item, parent, false)

        val myViewHolder = MyViewHolder(itemView, textWatcherMy)

        itemView.setOnClickListener {
            isGetting = true
            Log.d("TAG", "click listener = ${myViewHolder.adapterPosition}")
            positionCurrent = myViewHolder.adapterPosition
            itemFocusListener.invoke(myViewHolder.adapterPosition)
            myViewHolder.enableTextWatcher()
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
            isGetting = true
            myViewHolder.enableTextWatcher()
            return@setOnTouchListener true
        }

        return myViewHolder
    }

    override fun getItemCount(): Int {
        return listItems.size
    }

    override fun onBindViewHolder(holder: Adapter2.MyViewHolder, position: Int) {

        //Log.d("TAG", "onBindViewHolder position = ${holder.adapterPosition}")

        val itemState = listItems.get(position)
        holder.imageView.setImageDrawable(itemState.drawable)
        holder.textViewBase.text = itemState.base
        holder.textViewDescription.text = itemState.name
        holder.editText.setText(itemState.rate.toString())

        if(position == 0){
            holder.editText.requestFocus()
        }

        if((holder.adapterPosition == 0)&&(setedListener == false)){
            Log.d("TAG", "seted listener")
            setedListener = true
            holder.editText.setTag(1)
        }
    }

    private val textWatcherMy = object : TextWatcher {

        val scope = CoroutineScope(Dispatchers.Main + Job())

        override fun afterTextChanged(editable: Editable?) {
            /*editable?.toString()?.let {

                scope.launch {
                    Log.d("TAG", "In scope: ${it.toString()}")
                    _sharedFlowEditable.emit(it.toFloat())
                }
            }*/
        }

        override fun beforeTextChanged(s: CharSequence?,start: Int,count: Int,after: Int) {
        }

        override fun onTextChanged(s: CharSequence?,start: Int,before: Int,count: Int) {
            Log.d("TAG", "is Getting = ${isGetting}")
            jobTextListener = scope.launch {
                if(isGetting){

                    Log.d("TAG", "In scope: ${s}")
                    _sharedFlowEditable.emit(s.toString().toFloat())
                }
            }
        }
    }
}






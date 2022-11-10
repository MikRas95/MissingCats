package com.example.missingcats.models

import android.icu.text.DateFormat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.missingcats.R

class MyAdapter(
    private val items: List<Cat>,
    private val onItemClicked: (position: Int) -> Unit
) : RecyclerView.Adapter<MyAdapter.MyViewHolder>() {

    override fun getItemCount(): Int {
        return items.size
    }

    fun humanDate(date: Long): String? {
        val formatter = DateFormat.getDateInstance()
        if (date != null) {
            return formatter.format(date * 1000L) // seconds to milliseconds'
        }
        return null
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): MyViewHolder {
        // Create a new view, which defines the UI of the list item
        val view = LayoutInflater.from(viewGroup.context)
            //.inflate(R.layout.list_item_simple, viewGroup, false)
            .inflate(R.layout.list_item_card, viewGroup, false)
        return MyViewHolder(view, onItemClicked)
    }

    override fun onBindViewHolder(viewHolder: MyViewHolder, position: Int) {
        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        viewHolder.textView.text = items[position].toString()
        viewHolder.textViewCard.text = items[position].name
        viewHolder.textviewListItemDate.text = humanDate(items[position].date)
    }

    class MyViewHolder(itemView: View, private val onItemClicked: (position: Int) -> Unit) :
        RecyclerView.ViewHolder(itemView), View.OnClickListener {
        val textView: TextView = itemView.findViewById(R.id.textview_list_item)
        val textViewCard: TextView = itemView.findViewById(R.id.textView_cardName)
        val textviewListItemDate: TextView = itemView.findViewById(R.id.textview_list_item_Date)

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(view: View) {
            val position = bindingAdapterPosition
            // gradle     implementation "androidx.recyclerview:recyclerview:1.2.1"
            onItemClicked(position)
        }
    }
}
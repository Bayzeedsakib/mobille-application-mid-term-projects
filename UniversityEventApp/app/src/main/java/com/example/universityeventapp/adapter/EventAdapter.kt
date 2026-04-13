package com.example.universityeventapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.universityeventapp.R
import com.example.universityeventapp.model.Event

class EventAdapter(
    private var list: List<Event>,
    private val onClick: (Event) -> Unit
) : RecyclerView.Adapter<EventAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val title: TextView = view.findViewById(R.id.txtTitle)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_event, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val event = list[position]
        holder.title.text = event.title

        holder.itemView.setOnClickListener {
            onClick(event)
        }
    }

    override fun getItemCount() = list.size

    fun updateList(newList: List<Event>) {
        list = newList
        notifyDataSetChanged()
    }
}
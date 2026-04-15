package com.example.universityeventapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.universityeventapp.R
import com.example.universityeventapp.model.Speaker

class SpeakerAdapter(
    private val list: List<Speaker>
) : RecyclerView.Adapter<SpeakerAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val image: ImageView = view.findViewById(R.id.imgSpeaker)
        val name: TextView = view.findViewById(R.id.txtName)
        val designation: TextView = view.findViewById(R.id.txtDesignation)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_speaker, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val speaker = list[position]
        holder.name.text = speaker.name
        holder.designation.text = speaker.designation
        holder.image.setImageResource(speaker.imageRes)
    }

    override fun getItemCount() = list.size
}
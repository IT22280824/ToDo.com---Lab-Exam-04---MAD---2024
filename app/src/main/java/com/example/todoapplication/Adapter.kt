package com.example.todoapplication

import android.content.Intent
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.todoapplication.db.CardInfo

class Adapter(var data: List<CardInfo>): RecyclerView.Adapter<Adapter.viewHolder>() {

    class viewHolder(itemView: View) :RecyclerView.ViewHolder(itemView) {

        var title = itemView.findViewById<TextView>(R.id.title)
        var priority = itemView.findViewById<TextView>(R.id.priority)
        var layout = itemView.findViewById<LinearLayout>(R.id.mylayout)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewHolder {
        var itemView = LayoutInflater.from(parent.context).inflate(R.layout.view, parent, false)

        return viewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: viewHolder, position: Int) {

        holder.title.text = data[position].title
        holder.priority.text = data[position].priority

        when(data[position]. priority.toLowerCase()){

            "high" -> holder.layout.setBackgroundColor(Color.parseColor("#d96d6d"))
            "medium" -> holder.layout.setBackgroundColor(Color.parseColor("#d0d5f7"))
            else -> holder.layout.setBackgroundColor(Color.parseColor("#d4f0f7"))

        }

        holder.itemView.setOnClickListener {

            val intent = Intent(holder.itemView.context, UpdateCard::class.java)
            intent.putExtra("id", position)
            holder.itemView.context.startActivity(intent)

        }

    }

}
package com.example.moodguru

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.moodguru.parseDataModel.Advice

class SuggestionAdapter(val context: Context, val suggestionList: MutableList<Advice>):
    RecyclerView.Adapter<SuggestionAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // Specify the layout file to use for this item
        val view = LayoutInflater.from(context).inflate(R.layout.item_suggestion, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(suggestionList[position])
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvAdvice: TextView

        init {
            tvAdvice = itemView.findViewById(R.id.tvAdvice)
        }

        fun bind(advice: Advice) {
            tvAdvice.text = advice.getContent()
        }
    }

    override fun getItemCount(): Int {
        return suggestionList.size
    }
}


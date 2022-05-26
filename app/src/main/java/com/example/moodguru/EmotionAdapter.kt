package com.example.moodguru

import android.content.Context
import android.graphics.Color
import android.graphics.PorterDuff
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.graphics.drawable.DrawableCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.moodguru.parseDataModel.Emotion

class EmotionAdapter(val context: Context, val emotionList: MutableList<Emotion>, val onSelectHandler: OnSelectHandler)
    : RecyclerView.Adapter<EmotionAdapter.ViewHolder>() {

    val TAG = "EmotionAdapter"
    var selectedPosition = -1

    interface OnSelectHandler{
        fun onSelect(emotion: Emotion)
    }

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val ivEmoji: ImageView
        val tvEmojiAdj: TextView

        init {
            ivEmoji = itemView.findViewById(R.id.ivEmoji)
            tvEmojiAdj = itemView.findViewById(R.id.tvEmojiAdj)
        }

        fun bindDataAndMethod(emotion: Emotion){
            tvEmojiAdj.text = emotion.getAdjective()

            Glide.with(itemView.context)
                .load(emotion.getEmoji()?.url)
                .override(120, 120)
                .into(ivEmoji)

            // TODO: UI to make an emoji look selected
            ivEmoji.setOnClickListener {
                onSelectHandler.onSelect(emotion)
                notifyItemChanged(selectedPosition)
                selectedPosition = adapterPosition
                notifyItemChanged(selectedPosition)
                Log.d(TAG, "bindDataAndMethod: " + emotion.getAdjective())
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(context).inflate(R.layout.item_emoji, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindDataAndMethod(emotionList[position])

        if (selectedPosition == position){
            holder.itemView.setBackgroundResource(R.drawable.select_emoji)
            Log.d(TAG, "onBindViewHolder: ${emotionList[position].getAdjective()} selected")
        } else{
            holder.itemView.setBackgroundResource(0)
        }
    }

    override fun getItemCount(): Int = emotionList.size
}
package com.example.moodguru

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.moodguru.fragments.EmotionFragment
import com.example.moodguru.parseDataModel.Emotion
import com.example.moodguru.parseDataModel.Post
import com.parse.ParseQuery

const val POST_EXTRA = "POST_EXTRA"
class PostAdapter(val context: Context, val posts: List<Post>) : RecyclerView.Adapter<PostAdapter.ViewHolder>(){

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener{
        val tvDate: TextView
        val tvContent : TextView
        val ivEmoji: ImageView

        init {
            tvDate = itemView.findViewById(R.id.tvDashboardDate)
            tvContent = itemView.findViewById(R.id.tvDashboardContent)
            ivEmoji = itemView.findViewById(R.id.ivDashboardEmoji)
        }

        fun bind(post: Post){
            tvDate.text = post.getCreatedDate().toString()
            tvContent.text = post.getJournal()
            Glide.with(itemView.context)
                .load(post.getEmotion().getEmoji()?.url)
                .override(80, 80)
                .into(ivEmoji)
        }

        override fun onClick(v: View?){
            val post = posts[adapterPosition]
            val intent = Intent(context, DetailPostActivity::class.java)
            intent.putExtra(POST_EXTRA, post)
            context.startActivity(intent)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostAdapter.ViewHolder {
        // Specify the layout file to use for this item
        val view = LayoutInflater.from(context).inflate(R.layout.item_post, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: PostAdapter.ViewHolder, position: Int) {
        val post = posts.get(position)
        holder.bind(post)
    }

    override fun getItemCount(): Int {
        return posts.size
    }
}
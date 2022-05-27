package com.example.moodguru

import android.app.Activity
import android.app.ActivityOptions
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import android.util.Pair
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.app.ActivityOptionsCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.moodguru.fragments.EmotionFragment
import com.example.moodguru.parseDataModel.Emotion
import com.example.moodguru.parseDataModel.Post
import com.parse.ParseQuery

const val POST_EXTRA = "POST_EXTRA"
class PostAdapter(val context: Context, val posts: List<Post>) : RecyclerView.Adapter<PostAdapter.ViewHolder>(){

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener{
        val tvDate = itemView.findViewById<TextView>(R.id.tvDashboardDate)
        val tvContent = itemView.findViewById<TextView>(R.id.tvDashboardContent)
        val ivEmoji = itemView.findViewById<ImageView>(R.id.ivDashboardEmoji)

        init {
            itemView.setOnClickListener(this)
        }

        fun bind(post: Post){
            tvDate.text = post.getDate()
            tvContent.text = post.getJournal()
            Glide.with(itemView.context)
                .load(post.getEmotion().getEmoji()?.url)
                .override(80, 80)
                .into(ivEmoji)
        }

        override fun onClick(v: View?){
            val post = posts[adapterPosition]

            // 2. Use the intent system to navigate to new activity
            val intent = Intent(context, DetailPostActivity::class.java)
            intent.putExtra(POST_EXTRA, post)

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                // Apply activity transition
                val tvDatePair = Pair.create<View, String>(tvDate, "tvDate")
                val tvContentPair = Pair.create<View, String>(tvContent, "tvContent")
                val ivEmojiPair = Pair.create<View, String>(ivEmoji, "ivEmoji")
                val options = ActivityOptions.makeSceneTransitionAnimation(context as Activity,
                    tvDatePair, ivEmojiPair, tvContentPair)

                context.startActivity(intent, options.toBundle())
            } else {
                // Swap without transition
                context.startActivity(intent)
            }
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
package com.example.moodguru

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.moodguru.parseDataModel.Post

class DetailPostActivity : AppCompatActivity() {

    private lateinit var tvDate: TextView
    private lateinit var tvJournal: TextView
    private lateinit var ivEmoji: TextView
    private lateinit var rvSuggestions: RecyclerView
    private lateinit var tvQuote: TextView
    private lateinit var tvQuoteAuthor: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_post)

        tvDate = findViewById(R.id.tvDetailDate)
        tvJournal = findViewById(R.id.tvDetailContent)
        ivEmoji = findViewById(R.id.ivDetailEmoji)
        rvSuggestions = findViewById(R.id.rvDetailSuggestions)
        tvQuote = findViewById(R.id.tvDetailQuote)
        tvQuoteAuthor = findViewById(R.id.tvDetailQuoteAuthor)

        val post = intent.getParcelableExtra<Post>(POST_EXTRA) as Post
        tvDate.text = post.getCreatedDate()
        tvJournal.text = post.getJournal()
//        Glide.with(context)
//            .load(post.getEmotion().getEmoji()?.url)
//            .override(80, 80)
//            .into(ivEmoji)
//        rvSuggestions = post.getSuggestion()
//        tvQuote = post


    }
}
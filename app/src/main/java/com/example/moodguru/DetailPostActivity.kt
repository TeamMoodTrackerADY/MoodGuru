package com.example.moodguru

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.moodguru.parseDataModel.Post

class DetailPostActivity : AppCompatActivity() {

    private lateinit var tvDate: TextView
    private lateinit var tvJournal: TextView
    private lateinit var ivEmoji: ImageView
    private lateinit var tvSuggestion1: TextView
    private lateinit var tvSuggestion2: TextView
    private lateinit var tvSuggestion3: TextView
    private lateinit var tvQuote: TextView
    private lateinit var tvQuoteAuthor: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_post)

        tvDate = findViewById(R.id.tvDetailDate)
        tvJournal = findViewById(R.id.tvDetailContent)
        ivEmoji = findViewById(R.id.ivDetailEmoji)
        tvSuggestion1 = findViewById(R.id.tvDetailSuggestion1)
        tvSuggestion2 = findViewById(R.id.tvDetailSuggestion2)
        tvSuggestion3 = findViewById(R.id.tvDetailSuggestion3)
        tvQuote = findViewById(R.id.tvDetailQuote)
        tvQuoteAuthor = findViewById(R.id.tvDetailQuoteAuthor)

        val post = intent.getParcelableExtra<Post>(POST_EXTRA) as Post
        tvDate.text = post.getCreatedDate()
        tvJournal.text = post.getJournal()
        Glide.with(this)
            .load(post.getEmotion().getEmoji()?.url)
            .into(ivEmoji);
        val allSuggestions = post.getSuggestion().toString().split(';')
        tvSuggestion1.text = allSuggestions[0]
        tvSuggestion2.text = allSuggestions[1]
        tvSuggestion3.text = allSuggestions[2]
        tvQuote.text = post.getQuote()
        tvQuoteAuthor.text = post.getAuthor()

        val btnBack = findViewById<Button>(R.id.detailBackBtn)
        btnBack.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }
}
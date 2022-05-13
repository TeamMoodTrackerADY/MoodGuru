package com.example.moodguru

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.example.moodguru.fragments.ComposeFragment

class SuggestionActivity : AppCompatActivity() {


    companion object{
        val FILE_QUOTATION = "quotations.json"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_suggestion)

        loadQuotationsFromJson(this, FILE_QUOTATION)

        findViewById<Button>(R.id.backBtn).setOnClickListener {
            finish()
        }

        findViewById<Button>(R.id.doneBtn).setOnClickListener {
            // TODO: send everything related to this post to parse
            // TODO: go to dashboars
            val i: Intent = Intent(this, MainActivity::class.java)
            i.putExtra("signal", "from suggestion")
            startActivity(i)
            finish()
        }
    }

    private fun loadQuotationsFromJson(context: Context, fileName: String) {
        // return a list of quotations
        // Quotation class
    }


}
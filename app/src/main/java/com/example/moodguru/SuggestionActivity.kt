package com.example.moodguru

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

class SuggestionActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_suggestion)

//        findViewById<Button>(R.id.backBtn).setOnClickListener {
//            // TODO: Link back to Compose screen
//        }

        findViewById<Button>(R.id.doneBtn).setOnClickListener {
            goToMainActivity()
        }
    }

    private fun goToMainActivity() {
        val intent = Intent(this@SuggestionActivity, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}
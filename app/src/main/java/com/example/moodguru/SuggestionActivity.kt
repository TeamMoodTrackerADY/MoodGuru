package com.example.moodguru

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.moodguru.fragments.EmotionFragment
import com.example.moodguru.parseDataModel.Advice
import com.parse.ParseQuery
import kotlin.random.Random

class SuggestionActivity : AppCompatActivity() {

    lateinit var rvSuggestions: RecyclerView
    lateinit var suggestionAdapter: SuggestionAdapter
    val suggestionList: MutableList<Advice> = mutableListOf()

    companion object {
        val FILE_QUOTATION = "quotations.json"
        val TAG = "SuggestionActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_suggestion)
        rvSuggestions = findViewById(R.id.rvSuggestions)
        suggestionAdapter = SuggestionAdapter(this, suggestionList)

        rvSuggestions.adapter = suggestionAdapter
        rvSuggestions.layoutManager = LinearLayoutManager(this )

        fetchSuggestions()

        //loadQuotationsFromJson(this, FILE_QUOTATION)

        findViewById<Button>(R.id.backBtn).setOnClickListener {
            finish()
        }

        findViewById<Button>(R.id.doneBtn).setOnClickListener {
            // TODO: send everything related to this post to parse
            // TODO: go to dashboard
            val i: Intent = Intent(this, MainActivity::class.java)
            i.putExtra("signal", "from suggestion")
            startActivity(i)
            finish()
        }
    }

//    private fun loadQuotationsFromJson(context: Context, fileName: String) {
//        // return a list of quotations
//        // Quotation class
//    }

    private fun fetchSuggestions() {
        // Define the class we would like to query
        val query: ParseQuery<Advice> = ParseQuery.getQuery(Advice::class.java)

        query.findInBackground { list, e ->
            if (e != null) {
                Log.e(EmotionFragment.TAG, "fetchSuggestions failed", e)
                e.printStackTrace()
            } else {
                if (list != null) {
                    suggestionList.clear()
                    for (i in 1..3) {
                        val randomIndex = Random.nextInt(list.size)
                        val randomAdvice = list[randomIndex]
                        if(!suggestionList.contains(randomAdvice)) {
                            suggestionList.add(randomAdvice)
                        }
                    }

                    suggestionAdapter.notifyDataSetChanged()
                }
            }
        }
    }
}
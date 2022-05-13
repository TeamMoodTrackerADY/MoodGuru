package com.example.moodguru

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.moodguru.fragments.EmotionFragment
import com.example.moodguru.parseDataModel.Advice
import com.parse.ParseQuery
import org.json.JSONException
import kotlin.random.Random

class SuggestionActivity : AppCompatActivity() {

    lateinit var rvSuggestions: RecyclerView
    lateinit var suggestionAdapter: SuggestionAdapter
    lateinit var quoteQueue: RequestQueue
    lateinit var tvQuote: TextView
    lateinit var tvQuoteAuthor: TextView

    private var requestQueue: RequestQueue? = null

    val suggestionList: MutableList<Advice> = mutableListOf()

    companion object {
        val TAG = "SuggestionActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_suggestion)
        rvSuggestions = findViewById(R.id.rvSuggestions)
        suggestionAdapter = SuggestionAdapter(this, suggestionList)

        rvSuggestions.adapter = suggestionAdapter
        rvSuggestions.layoutManager = LinearLayoutManager(this )

        tvQuote = findViewById(R.id.tvQuote)
        tvQuoteAuthor = findViewById(R.id.tvQuoteAuthor)
        requestQueue = Volley.newRequestQueue(this)

        fetchSuggestions()

        loadQuoteFromJson(this)

        findViewById<Button>(R.id.backBtn).setOnClickListener {
            finish()
        }

        findViewById<Button>(R.id.doneBtn).setOnClickListener {
            // TODO: send everything related to this post to parse
            // TODO: go to dashboard
            val i = Intent(this, MainActivity::class.java)
            i.putExtra("signal", "from suggestion")
            startActivity(i)
            finish()
        }
    }

    private fun loadQuoteFromJson(context: Context) {
        quoteQueue = Volley.newRequestQueue(context)

        val url = "https://jsonkeeper.com/b/UA8Q"

        val request = JsonObjectRequest(Request.Method.GET, url, null, {
                response -> try {
                    val jsonArray = response.getJSONArray("quotes")
                    val randomIndex = (0..jsonArray.length()).shuffled().first()

                    var randomQuote = jsonArray.getJSONObject(randomIndex)
                    var quote = randomQuote.getString("quote")
                    var author = randomQuote.getString("author")
                    tvQuote.text = quote
                    tvQuoteAuthor.text = author
                }
                catch (e: JSONException) {
                    e.printStackTrace()
                }
                }, { error -> error.printStackTrace() })

        requestQueue?.add(request)

    }

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
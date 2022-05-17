package com.example.moodguru

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.moodguru.fragments.ComposeFragment
import com.example.moodguru.fragments.EmotionFragment
import com.example.moodguru.parseDataModel.Advice
import com.example.moodguru.parseDataModel.Emotion
import com.example.moodguru.parseDataModel.Post
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
    lateinit var adviceArray: Array<String>
    lateinit var quote: String

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

        // Get the selected adjective from the Compose fragment
        val adj = intent.getStringExtra(ComposeFragment.KEY_ADJ_TO_SUGG)
        Log.d(TAG, "suggestion based on: $adj")

        // Get the user's input journal from the Compose fragment
        val journal = intent.getStringExtra(ComposeFragment.KEY_JOURNAL_TO_SUGG)
        Log.d(TAG, "journal: $journal")

        if (adj != null) {
            fetchSuggestions(adj)
        }

        // Load and display 1 "Quote of the day"
        quote = loadQuoteFromJson(this)

        // Go back to the Compose screen
        findViewById<Button>(R.id.backBtn).setOnClickListener {
            finish()
        }

        // After we click "Done", all users' inputs should be posted to Parse
        findViewById<Button>(R.id.doneBtn).setOnClickListener {
            // TODO: Delete dummy variable for rating later
            val rating = 3

            val emotion = intent.extras?.getParcelable<Emotion>(ComposeFragment.KEY_EMOTION_TO_SUGG)

            // Send everything related to this post to parse
            if (journal != null && adj != null && emotion != null && quote != null) {
                submitPost(journal, adj, rating, emotion, quote)
            }

            // Go to dashboard
            val i = Intent(this, MainActivity::class.java)
            i.putExtra("signal", "from suggestion")
            startActivity(i)
            finish()
        }
    }

    fun submitPost(journal: String, adjective: String, rating: Int,
                   emotion: Emotion, quote: String) {
        // Create the Post object
        val post = Post()
        post.putJournal(journal)
        post.putAdjective(adjective)
        post.putRating(rating)
        post.putEmotion(emotion)
        //post.putSuggestion(adviceArray)
        post.putQuote(quote)

        post.saveInBackground{exception ->
            if(exception != null) {
                // Something went wrong
                Log.e(TAG, "Error while saving post!")
                exception.printStackTrace()
                Toast.makeText(this, "Error while saving post!", Toast.LENGTH_SHORT).show()
            } else {
                Log.i(TAG, "Successfully saved post!")
                Toast.makeText(this, "Posted!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun loadQuoteFromJson(context: Context): String {
        quoteQueue = Volley.newRequestQueue(context)

        val url = "https://jsonkeeper.com/b/R2BK"

        var quote = ""

        val request = JsonObjectRequest(Request.Method.GET, url, null, {
                response -> try {
                    val jsonArray = response.getJSONArray("quotes")
                    val randomIndex = (0..jsonArray.length()).shuffled().first()

                    var randomQuote = jsonArray.getJSONObject(randomIndex)
                    quote = randomQuote.getString("quote")
                    var author = randomQuote.getString("author")
                    tvQuote.text = quote
                    tvQuoteAuthor.text = author
                }
                catch (e: JSONException) {
                    e.printStackTrace()
                }
                }, { error -> error.printStackTrace() })

        requestQueue?.add(request)

        return tvQuote.text.toString()
    }

    private fun fetchSuggestions(adj: String) {
        // Define the class we would like to query
        val query: ParseQuery<Advice> = ParseQuery.getQuery(Advice::class.java)
        query.whereEqualTo(Emotion.KEY_ADJ, adj)
        query.findInBackground { list, e ->
            if (e != null) {
                Log.e(EmotionFragment.TAG, "fetchSuggestions failed", e)
                e.printStackTrace()
            } else {
                if (list != null) {
                    suggestionList.clear()
                    while(suggestionList.size < 3) {
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
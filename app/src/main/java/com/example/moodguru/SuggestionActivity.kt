package com.example.moodguru

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
import com.example.moodguru.fragments.ChartFragment
import com.example.moodguru.fragments.ComposeFragment
import com.example.moodguru.fragments.EmotionFragment
import com.example.moodguru.parseDataModel.*
import com.parse.*
import org.json.JSONException
import kotlin.random.Random


lateinit var adviceMutableList: MutableList<String>

class SuggestionActivity : AppCompatActivity() {

    private lateinit var rvSuggestions: RecyclerView
    private lateinit var suggestionAdapter: SuggestionAdapter
    private lateinit var quoteQueue: RequestQueue
    private lateinit var tvQuote: TextView
    private lateinit var tvQuoteAuthor: TextView

    private var requestQueue: RequestQueue? = null

    private val suggestionList: MutableList<Advice> = mutableListOf()
    private var advice: String = ""
    private var quote: String = ""

    companion object {
        const val TAG = "SuggestionActivity"
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

        // Get the user's input journal from the Compose fragment
        val journal = intent.getStringExtra(ComposeFragment.KEY_JOURNAL_TO_SUGG)

        // TODO: Delete dummy variable for rating later
        val rating = intent.getFloatExtra(ComposeFragment.KEY_RATING_TO_SUGG, 0F)

        // Get the whole "emotion" class based on the user's selection
        val emotion = intent.extras?.getParcelable<Emotion>(ComposeFragment.KEY_EMOTION_TO_SUGG)

        // Create a new post
        val post = Post()

        if (adj != null) {
            adviceMutableList = mutableListOf()

            // Define the class we would like to query to fetch the suggestions list
            val query: ParseQuery<Advice> = ParseQuery.getQuery(Advice::class.java)
            query.whereEqualTo(Emotion.KEY_ADJ, adj)
            query.findInBackground { list, e ->
                if (e != null) {
                    Log.e(EmotionFragment.TAG, "fetchSuggestions failed", e)
                    e.printStackTrace()
                } else {
                    if (list != null) {
                        suggestionList.clear()

                        // Randomly generate 3 pieces of advices based on the selected emotion
                        while(suggestionList.size < 3) {
                            val randomIndex = Random.nextInt(list.size)
                            val randomAdvice = list[randomIndex]
                            if(!suggestionList.contains(randomAdvice)) {
                                suggestionList.add(randomAdvice)
                                if(randomAdvice != null){
                                    adviceMutableList.add(randomAdvice.getContent().toString())
                                }
                            }
                        }

                        // Format the "advice" string to "Advice 1; Advice 2; Advice 3"
                        advice = advice.plus(adviceMutableList.elementAt(0)).plus(";")
                            .plus(adviceMutableList.elementAt(1)).plus(";")
                            .plus(adviceMutableList.elementAt(2))

                        // Submit everything to post before the user clicks "Done"
                        if (journal != null && emotion != null) {
                            post.putUser(ParseUser.getCurrentUser())
                            post.putJournal(journal)
                            post.putAdjective(adj)
                            post.putRating(rating)
                            post.putEmotion(emotion)
                            post.putSuggestion(advice)
                            post.putDate()
                            post.putDDate()
                            fetchRandomQuote(post)
                        }

                        suggestionAdapter.notifyDataSetChanged()

                        // Only save the info if the user clicks Done
                        findViewById<Button>(R.id.doneBtn).setOnClickListener {

                            // After we click "Done", all users' inputs should be posted to Parse
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

                            calculateAvgRating(post)

                            // Go back to dashboard with the new post on top
                            val i = Intent(this, MainActivity::class.java)
                            i.putExtra("signal", "from suggestion")
                            startActivity(i)
                            finish()
                        }

                        // Go back to the Compose screen
                        findViewById<Button>(R.id.backBtn).setOnClickListener {
                            finish()
                            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
                        }
                    }
                }
            }
        }
    }

    private fun fetchRandomQuote(post: Post){
        // Load and display 1 "Quote of the day"
        quoteQueue = Volley.newRequestQueue(this)

        // The URL that stores our JSON database
        val url = "https://jsonkeeper.com/b/R2BK"

        // Make a request to fetch a random quote
        val request = JsonObjectRequest(Request.Method.GET, url, null, {
                response -> try {
            val jsonArray = response.getJSONArray("quotes")
            val randomIndex = (0..jsonArray.length()).shuffled().first()

            val randomQuote = jsonArray.getJSONObject(randomIndex)
            quote = randomQuote.getString("quote")
            val author = randomQuote.getString("author")
            tvQuote.text = quote
            tvQuoteAuthor.text = author

            // Put the quote and the author to correct column on Parse
            post.putQuote(quote)
            post.putAuthor(author)
        }
        catch (e: JSONException) {
            e.printStackTrace()
        }
        }, { error -> error.printStackTrace() })

        requestQueue?.add(request)
    }

    private fun calculateAvgRating(post: Post){
        // Specify which class to query
        val chartQuery: ParseQuery<Chart> = ParseQuery.getQuery(Chart::class.java)

        chartQuery.include(Chart.KEY_USER)
            .include(Chart.KEY_DATE)
            .include(Chart.KEY_DDATE)
            .include(Chart.KEY_AVGRATING)
            .include(Chart.KEY_POSTCOUNT)

        // Return posts in descending order (newer posts appear first)
        chartQuery.whereEqualTo(Chart.KEY_USER, ParseUser.getCurrentUser())
        chartQuery.whereEqualTo(Chart.KEY_DATE, post.getDate())
        chartQuery.findInBackground { charts, e ->
            Log.i(TAG, "in find in background: " + chartQuery.find().toString())
            if (e != null){
                // Something went wrong
                Log.i(TAG, "Error fetching chart")
                e.printStackTrace()
            }else{
                // the avg rating already exists for this date
                if (chartQuery.find().size != 0){
                    var chart = charts[0]
                    Log.i(TAG, "Date: " + chart.getDate() + ", avg rating: " + chart.getAvgRating() + ", count: " + chart.getPostCount())

                    // calculate the new avg rating
                    val oldAvg = chart.getAvgRating()
                    val oldCount = chart.getPostCount()
                    val oldRating = oldAvg!! * oldCount!!
                    val newRating = post.getRating()?.plus(oldRating)
                    val newCount = oldCount+1f
                    if (newRating != null) {
                        chart.putAvgRating(newRating/newCount)
                    }
                    if (oldCount != null) {
                        chart.putPostCount(oldCount + 1)
                    }
                    chart.putDate(post.getDate().toString())
                    chart.putDDate(post.getDDate()!!)
                    chart.putUser(ParseUser.getCurrentUser())
                    chart.saveInBackground { e ->
                        if (e == null) {
                            // Saved successfully.
                            Log.i(TAG, "chart saved")
                            Log.i(TAG, "Date: " + chart.getDate() + ", avg rating: " + chart.getAvgRating() + ", count: " + chart.getPostCount())
                        } else {
                            // The save failed.
                            Log.i(TAG, "chart save failed")
                        }
                    }
                } else {
                    Log.i(TAG, "there isn't any data for this date: " + post.getDate())
                    // create a new chart for this date
                    val newChart = Chart()
                    newChart.putDate(post.getDate().toString())
                    newChart.putDDate(post.getDDate()!!)
                    newChart.putAvgRating(post.getRating()!!.toFloat())
                    newChart.putPostCount(1f)
                    newChart.putUser(ParseUser.getCurrentUser())

                    // post to parse
                    newChart.saveInBackground { e ->
                        if (e == null) {
                            // Saved successfully.
                            Log.i(TAG, "new chart saved")
                        } else {
                            // The save failed.
                            Log.i(TAG, "new chart failed to save")
                        }
                    }
                }
            }
        }
    }
}

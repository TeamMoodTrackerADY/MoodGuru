package com.example.moodguru.fragments

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.graphics.drawable.DrawableCompat
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.setFragmentResultListener
import com.bumptech.glide.Glide
import com.example.moodguru.EmotionAnalyzer
//import com.example.moodguru.EmotionAnalyzer
//import com.example.moodguru.EmotionAnalyzer
import com.example.moodguru.R
import com.example.moodguru.SuggestionActivity
import com.example.moodguru.parseDataModel.Emotion
//import com.vader.sentiment.analyzer.SentimentAnalyzer

//import com.vader.sentiment.analyzer.SentimentAnalyzer

class ComposeFragment : Fragment() {

    lateinit var etJournal: EditText
    lateinit var ivEmoji: ImageView
    lateinit var btnContinue: Button
    lateinit var tvEmotionLabel: TextView
    lateinit var ratingBar: RatingBar

    var adjToSend:String? = null

    companion object{
        val TAG = "ComposeFragment"
        val KEY_REQUEST_EMO = "select_an_emotion"
        val HINT = "What makes you %s?"
        val KEY_ADJ_TO_SUGG = "send_adj_to_suggestion"
        val KEY_RATING_TO_SUGG = "send_rating_to_suggestion"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_compose, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setFragmentResultListener(KEY_REQUEST_EMO)
        {
            key, bundle ->
            val emotion = bundle.getParcelable<Emotion>(EmotionFragment.KEY_SELECT_EMO)
            Log.d(TAG, "ready to display: " + emotion?.getAdjective())
            if (emotion != null) {
                adjToSend = emotion.getAdjective()
                setEmotionToDisplay(emotion)
                etJournal.hint = String.format(HINT, emotion.getAdjective()?.lowercase())
            }
        }
    }

    private fun setEmotionToDisplay(emotion: Emotion) {
        tvEmotionLabel.text = emotion.getAdjective()
        Glide.with(requireContext())
            .load(emotion.getEmoji()?.url)
            .into(ivEmoji)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        etJournal = view.findViewById(R.id.etJournalContent)
        ivEmoji = view.findViewById(R.id.ivEmoji)
        tvEmotionLabel = view.findViewById(R.id.tvEmotionLabel)
        btnContinue = view.findViewById(R.id.btnContinue)
        ratingBar = view.findViewById(R.id.rbEmotionLevel)


//        EmotionAnalyzer.create().analyze("This is a freaking mad day. God I love it!")

       etJournal.addTextChangedListener(object: TextWatcher{
           override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

           override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
               if (!s.isNullOrEmpty()){
                   val f = EmotionAnalyzer.analyze(s.toString(), requireContext())
                   updateRatingBar(f)
               }
           }

           override fun afterTextChanged(s: Editable?) {}

       })
        
        ivEmoji.setOnClickListener {
            EmotionFragment.showEmotionFragment(requireActivity().supportFragmentManager)
        }

        // Send current adjective & rating to SuggestionActivity
        btnContinue.setOnClickListener {
            val i = Intent(requireContext(), SuggestionActivity::class.java)
//            Log.d(TAG, "rating is: " + ratingBar.rating)

            if (adjToSend == null){
                Toast.makeText(requireContext(),
                    "Select an emotion to continue", Toast.LENGTH_SHORT)
                    .show()
            } else{
                i.putExtra(KEY_ADJ_TO_SUGG, adjToSend)
                i.putExtra(KEY_RATING_TO_SUGG, ratingBar.rating)
                startActivity(i)
                // TODO: transaction
            }

        }


    }

    private fun updateRatingBar(rating: Float) {
        ratingBar.rating = Math.abs(rating * 5.0).toFloat()
        if (rating > 0.5)
            DrawableCompat.setTint(ratingBar.progressDrawable, resources.getColor(R.color.orange_pos))
        else if (rating < -0.5)
            DrawableCompat.setTint(ratingBar.progressDrawable, resources.getColor(R.color.blue_neg))
        else
            DrawableCompat.setTint(ratingBar.progressDrawable, resources.getColor(R.color.logo_yellow))
        Log.d(TAG, "emo = $rating")
    }
}
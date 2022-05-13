package com.example.moodguru.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.setFragmentResultListener
import com.bumptech.glide.Glide
import com.example.moodguru.R
import com.example.moodguru.SuggestionActivity
import com.example.moodguru.parseDataModel.Emotion

class ComposeFragment : Fragment() {

    lateinit var etJournal: EditText
    lateinit var ivEmoji: ImageView
    lateinit var btnContinue: Button
    lateinit var tvEmotionLabel: TextView

    companion object{
        val TAG = "ComposeFragment"
        val KEY_REQUEST_EMO = "select_an_emotion"
        val HINT = "What makes you feel %s?"
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
                setEmotionToDisplay(emotion)
                etJournal.hint = String.format(HINT, emotion.getAdjective()?.lowercase())
            }
        }
    }

    private fun setEmotionToDisplay(emotion: Emotion) {
        tvEmotionLabel.text = emotion.getAdjective()
        Glide.with(requireContext()).load(emotion.getEmoji()?.url).into(ivEmoji)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        etJournal = view.findViewById(R.id.etJournalContent)
        ivEmoji = view.findViewById(R.id.ivEmoji)
        tvEmotionLabel = view.findViewById(R.id.tvEmotionLabel)
        btnContinue = view.findViewById(R.id.btnContinue)

        ivEmoji.setOnClickListener {
            EmotionFragment.showEmotionFragment(requireActivity().supportFragmentManager)
        }

        btnContinue.setOnClickListener {
            val i = Intent(requireContext(), SuggestionActivity::class.java)
            startActivity(i)
        }


        // TODO: UI add title (optional), add colum in db
        //

    }
}
package com.example.moodguru.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import com.example.moodguru.R
import com.example.moodguru.SuggestionActivity

class ComposeFragment : Fragment() {

    lateinit var etJournal: EditText
    lateinit var ivEmoji: ImageView
    lateinit var btnContinue: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_compose, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        etJournal = view.findViewById(R.id.etJournalContent)
        ivEmoji = view.findViewById(R.id.ivEmoji)
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
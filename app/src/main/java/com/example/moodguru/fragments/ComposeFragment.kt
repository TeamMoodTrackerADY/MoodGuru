package com.example.moodguru.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import com.example.moodguru.R

class ComposeFragment : Fragment() {

    lateinit var etJournal: EditText
    lateinit var ivEmoji: ImageView

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

        ivEmoji.setOnClickListener {
            EmotionFragment.showEmotionFragment(requireActivity().supportFragmentManager)
        }


    }
}
package com.example.moodguru.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.moodguru.R

class DashboardFragment : Fragment() {



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_dashboard, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val tvAdvice1 = view.findViewById<TextView>(R.id.tvSuggestion1)
        val tvAdvice2 = view.findViewById<TextView>(R.id.tvSuggestion2)
        val tvAdvice3 = view.findViewById<TextView>(R.id.tvSuggestion3)

        fetchSuggestion()
    }

    private fun fetchSuggestion() {
        // TODO: query for advice whose 'adjective' equals 'sad' (constraint)

        // TODO: return a list of such query

        // TODO: randomized index, choose 3 distinct from X

        // TODO: glue the 3 advice to Suggestions TextView
    }
}
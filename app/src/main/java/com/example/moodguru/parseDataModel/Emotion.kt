package com.example.moodguru.parseDataModel

import com.parse.ParseClassName
import com.parse.ParseObject

@ParseClassName("Emotion")
class Emotion(val _adjective: String, val _emoji: String, val _rating: Int): ParseObject() {

    companion object{
        val KEY_ADJ = "adjective"
        val KEY_EMOJI = "emoji"
        val KEY_RATING = "rating"

    }


}
package com.example.moodguru.parseDataModel

import android.util.Log
import com.parse.*

@ParseClassName("Emotion")
class Emotion : ParseObject() {

    companion object{
        val TAG = "Emotion"
        val KEY_ADJ = "adjective"
        val KEY_EMOJI = "emoji"

        fun getEmojiByAdjective(adj: String): ParseFile? {
            val query:ParseQuery<Emotion> = ParseQuery.getQuery(Emotion::class.java)
            return query.whereEqualTo(KEY_ADJ, adj).first.getEmoji()
        }
    }

    fun getAdjective() = getString(KEY_ADJ)
    fun getEmoji() = getParseFile(KEY_EMOJI)

}
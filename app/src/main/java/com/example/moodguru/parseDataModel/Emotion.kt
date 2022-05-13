package com.example.moodguru.parseDataModel

import com.parse.ParseClassName
import com.parse.ParseFile
import com.parse.ParseObject

@ParseClassName("Emotion")
class Emotion : ParseObject() {

    companion object{
        val KEY_ADJ = "adjective"
        val KEY_EMOJI = "emoji"
    }

    fun getAdjective() = getString(KEY_ADJ)
    fun getEmoji() = getParseFile(KEY_EMOJI)

}
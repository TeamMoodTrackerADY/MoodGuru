package com.example.moodguru.parseDataModel

import com.parse.ParseClassName
import com.parse.ParseObject

@ParseClassName("Advice")
class Advice: ParseObject() {

    companion object{
        val KEY_ADJ = "adjective"
        val KEY_CONTENT = "content"
    }

    fun getAdjective() = getString(KEY_ADJ)
    fun getContent() = getString(KEY_CONTENT)
}
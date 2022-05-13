package com.example.moodguru.parseDataModel

import com.parse.ParseClassName
import com.parse.ParseObject

@ParseClassName("Quote")
class Quote: ParseObject() {
    companion object{
        val KEY_CONTENT = "content"
        val KEY_AUTHOR = "author"
    }

    fun getContent() = getString(KEY_CONTENT)
    fun getAuthor() = getString(KEY_AUTHOR)
}
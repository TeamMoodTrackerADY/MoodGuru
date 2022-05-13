package com.example.moodguru.parseDataModel

import com.parse.ParseClassName
import com.parse.ParseObject
import com.parse.ParseUser

@ParseClassName("Post")
class Post: ParseObject() {
    companion object{
        val KEY_ADJ = "adjective"
        val KEY_RATING = "rating"

        val KEY_JOURNAL = "journal"
        val KEY_AUTHOR = "author"

        val KEY_SUGGESTION = "suggestion"
    }

    fun getAdjective() = getString(KEY_ADJ)
    fun putAdjective(adj: String) = put(KEY_ADJ, adj)

    fun getRating() = getInt(KEY_RATING)
    fun putRating(rating: Int) = put(KEY_RATING, rating)

    fun getJournal() = getString(KEY_JOURNAL)
    fun putJournal(journal: String) = put(KEY_JOURNAL, journal)

    fun getAuthor() = getParseUser(KEY_AUTHOR)
    fun putAuthor(author: ParseUser) = put(KEY_AUTHOR, author)

    fun getSuggestion() = getParseObject(KEY_SUGGESTION)
//    fun putSuggestion() = put(KEY_SUGGESTION, )
}
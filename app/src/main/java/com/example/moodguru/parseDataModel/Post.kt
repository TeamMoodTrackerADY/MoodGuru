package com.example.moodguru.parseDataModel

import com.parse.ParseClassName
import com.parse.ParseObject
import com.parse.ParseUser
import java.util.*

@ParseClassName("Post")
class Post: ParseObject() {
    companion object{
        val KEY_ADJ = "adjective"
        val KEY_RATING = "rating"

        val KEY_JOURNAL = "journal"
        val KEY_AUTHOR = "author"

        val KEY_SUGGESTION = "suggestion"
        val KEY_DATE = "createdAt"
        val KEY_EMOTION = "emotion"

        val KEY_QUOTE = "quote"
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
    fun putSuggestion(advice: Array<String>) = put(KEY_SUGGESTION, advice)

    fun getCreatedDate(): String {
        val originalDate = this.getCreatedAt().toString() //Sat May 14 19:26:07 PDT 2022
        val day = originalDate.substring(0, 3)
        val month = originalDate.substring(4, 7)
        val date = originalDate.substring(8, 10)
        val year = originalDate.substring(24, 28)

        val completeDate = month + " " + date + ", " + year + " (" + day + ")"
        return completeDate
    }
    fun putDate(date: Date) = put(KEY_DATE, date)

    fun getEmotion(): Emotion {
        val emotion = getParseObject(KEY_EMOTION) as Emotion
        return emotion
    }

    fun putEmotion(emotion: Emotion) = put(KEY_EMOTION, emotion)

    fun getQuote() = getString(KEY_QUOTE)
    fun putQuote(quote: String) = put(KEY_QUOTE, quote)
}
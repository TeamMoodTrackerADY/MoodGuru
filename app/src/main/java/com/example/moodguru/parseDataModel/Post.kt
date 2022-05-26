package com.example.moodguru.parseDataModel

import android.util.Log
import com.parse.ParseClassName
import com.parse.ParseObject
import com.parse.ParseUser
import java.util.*

@ParseClassName("Post")
class Post: ParseObject() {
    companion object{
        val KEY_USER = "user"

        val KEY_ADJ = "adjective"
        val KEY_RATING = "rating"

        val KEY_JOURNAL = "journal"
        val KEY_AUTHOR = "author"

        val KEY_SUGGESTION = "advice"
        val KEY_DATE = "createdAt"
        val KEY_EMOTION = "emotion"

        val KEY_QUOTE = "quote"

        val KEY_DASHBOARD = "includeInDashboard"
    }

    fun getUser() = getParseUser(KEY_USER)
    fun putUser(user: ParseUser) = put(KEY_USER, user)

    fun getAdjective() = getString(KEY_ADJ)
    fun putAdjective(adj: String) = put(KEY_ADJ, adj)

    fun getRating() = getNumber(KEY_RATING)?.toFloat()
    fun putRating(rating: Float) = put(KEY_RATING, rating)

    fun getJournal() = getString(KEY_JOURNAL)
    fun putJournal(journal: String) = put(KEY_JOURNAL, journal)

    fun getAuthor() = getString(KEY_AUTHOR)
    fun putAuthor(author: String) = put(KEY_AUTHOR, author)

    fun getSuggestion() = getString(KEY_SUGGESTION)
    fun putSuggestion(advice: String) = put(KEY_SUGGESTION, advice)

    fun getQuote() = getString(KEY_QUOTE)
    fun putQuote(quote: String) = put(KEY_QUOTE, quote)

    fun getCreatedDate(): String {
        var c = Calendar.getInstance(Locale.getDefault())
//        confirm local time:
//        Log.i("time", c.get(Calendar.HOUR).toString())
//        Log.i("time", c.get(Calendar.MINUTE).toString())

        val dayNum = (c.get(Calendar.DAY_OF_WEEK).toString().toInt() + c.getFirstDayOfWeek())%7
        var day : String
        when (dayNum){
            1 -> day = "Sun"
            2 -> day = "Mon"
            3 -> day = "Tue"
            4 -> day = "Wed"
            5 -> day = "Thu"
            6 -> day = "Fri"
            else -> day = "Sat"
        }

        val monthNum = c.get(Calendar.MONTH).toString().toInt()
        var month : String
        when (monthNum){
            0 -> month = "Jan"
            1 -> month = "Feb"
            2 -> month = "Mar"
            3 -> month = "Apr"
            4 -> month = "May"
            5 -> month = "Jun"
            6 -> month = "Jul"
            7 -> month = "Aug"
            8 -> month = "Sep"
            9 -> month = "Oct"
            10 -> month = "Nov"
            else -> month = "Dec"
        }
        val date = c.get(Calendar.DATE).toString()
        val year = c.get(Calendar.YEAR).toString()

        val dateToShow = month + " " + date + ", " + year + " (" + day + ")"
        return dateToShow
    }
    fun putDate(date: Date) = put(KEY_DATE, date)

    fun getEmotion(): Emotion {
        val emotion = getParseObject(KEY_EMOTION) as Emotion
        return emotion
    }

    fun putEmotion(emotion: Emotion) = put(KEY_EMOTION, emotion)

    fun putDashboard(includeInDashboard: Int) = put(KEY_DASHBOARD, includeInDashboard)
}
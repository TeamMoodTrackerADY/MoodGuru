package com.example.moodguru.parseDataModel

import com.parse.ParseClassName
import com.parse.ParseObject
import com.parse.ParseUser
import java.util.*

@ParseClassName("Chart")
class Chart: ParseObject() {
    companion object{
        val KEY_AVGRATING= "avgRating"
        val KEY_DATE = "localTime"
        val KEY_DDATE = "dateTypeDate"
        val KEY_POSTCOUNT = "postCount"
        val KEY_USER = "user"
    }

    fun getAvgRating() = getNumber(KEY_AVGRATING)?.toFloat()
    fun putAvgRating(rating: Float) = put(KEY_AVGRATING, rating)

    fun getDate() = getString(KEY_DATE)
    fun putDate(date: String) = put(KEY_DATE, date)

    fun getDDate() = getDate(KEY_DDATE)
    fun putDDate(date: Date) = put(KEY_DDATE, date)

    fun getPostCount() = getNumber(KEY_POSTCOUNT)?.toFloat()
    fun putPostCount(count: Float) = put(KEY_POSTCOUNT, count)

    fun getUser() = getParseUser(Post.KEY_USER)
    fun putUser(user: ParseUser) = put(Post.KEY_USER, user)
}
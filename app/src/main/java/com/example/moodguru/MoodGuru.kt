package com.example.moodguru

import android.app.Application
import com.example.moodguru.parseDataModel.Advice
import com.example.moodguru.parseDataModel.Emotion
import com.example.moodguru.parseDataModel.Post
import com.parse.Parse
import com.parse.ParseObject

class MoodGuru : Application() {
    override fun onCreate() {
        super.onCreate()
        ParseObject.registerSubclass(Post::class.java)
        ParseObject.registerSubclass(Emotion::class.java)
        ParseObject.registerSubclass(Advice::class.java)

        Parse.initialize(
            Parse.Configuration.Builder(this)
                .applicationId(getString(R.string.back4app_app_id))
                .clientKey(getString(R.string.back4app_client_key))
                .server(getString(R.string.back4app_server_url))
                .build())
    }
}
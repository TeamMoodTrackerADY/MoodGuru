package com.example.moodguru

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.parse.ParseException
import com.parse.ParseObject
import com.parse.SaveCallback

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val firstObject = ParseObject("FirstClass")
        firstObject.put("message","Hey ! First message from android. Parse is now connected")
        firstObject.saveInBackground(object : SaveCallback{
            override fun done(e: ParseException?) {
                if (e != null){
                    Log.d("good", "done: ")
                }else{
                    Log.e("bad", "error $e")
                }
            }

        })
    }
}
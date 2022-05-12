package com.example.moodguru

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.android.material.bottomnavigation.BottomNavigationView
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
                if (e == null){
                    Log.d("good", "done: ")
                }else{
                    Log.e("bad", "error $e")
                }
            }

        })

        // Bottom Navigator on item click listener (dashboard/compose/chart)
        findViewById<BottomNavigationView>(R.id.bottom_navigation).setOnItemSelectedListener {
            item ->
            when (item.itemId){
                // dashboard button listener
                R.id.action_dashboard -> {

                    // Toast.makeText(this, "Dashboard", Toast.LENGTH_SHORT).show()
                }

                // compose button listener
                R.id.action_compose -> {

                    // Toast.makeText(this, "Compose", Toast.LENGTH_SHORT).show()
                }

                // chart button listener
                R.id.action_chart -> {

                    // Toast.makeText(this, "Chart", Toast.LENGTH_SHORT).show()
                }
            }

            // user interaction on the item has been handled
            true
        }


    }
}
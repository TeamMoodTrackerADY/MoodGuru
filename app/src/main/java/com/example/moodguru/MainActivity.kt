package com.example.moodguru

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.moodguru.fragments.ChartFragment
import com.example.moodguru.fragments.ComposeFragment
import com.example.moodguru.fragments.DashboardFragment
import com.example.moodguru.fragments.EmotionFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.parse.ParseException
import com.parse.ParseObject
import com.parse.SaveCallback

class MainActivity : AppCompatActivity() {
    val TAG = "MainLog"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val firstObject = ParseObject("FirstClass")
        firstObject.put("message","Hey ! First message from android. Parse is now connected")
        firstObject.saveInBackground { e ->
            if (e == null) {
                Log.d(TAG, "firstObject successfully saved")
            } else {
                Log.e(TAG, "firstObject failed to save $e")
            }

        }




        // Bottom Navigator on item click listener (dashboard/compose/chart)
        val fragmentManager = supportFragmentManager
        val btmNavi = findViewById<BottomNavigationView>(R.id.bottom_navigation)

        lateinit var fragmentToShow: Fragment


        btmNavi.setOnItemSelectedListener { item ->

            when (item.itemId){
                // dashboard button listener
                R.id.action_dashboard -> {
                    fragmentToShow = DashboardFragment()
                    // Toast.makeText(this, "Dashboard", Toast.LENGTH_SHORT).show()
                }

                // compose button listener
                R.id.action_compose -> {
                    fragmentToShow = ComposeFragment()
                    // Toast.makeText(this, "Compose", Toast.LENGTH_SHORT).show()
                }

                // chart button listener
                R.id.action_chart -> {
                    fragmentToShow = ChartFragment()
                    // Toast.makeText(this, "Chart", Toast.LENGTH_SHORT).show()
                }
                else -> fragmentToShow = ComposeFragment()
            }
            fragmentManager.beginTransaction().replace(R.id.frgContainer, fragmentToShow).commit()

            // user interaction on the item has been handled
            true
        }

        // default
        btmNavi.selectedItemId = R.id.action_compose

        // if get signal from suggestion activity, land on dashboard
        if (intent.getStringExtra("signal").equals("from suggestion")){
            btmNavi.selectedItemId = R.id.action_dashboard
        }



    }
}
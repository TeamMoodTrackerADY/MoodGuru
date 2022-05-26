package com.example.moodguru

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import com.example.moodguru.fragments.ChartFragment
import com.example.moodguru.fragments.ComposeFragment
import com.example.moodguru.fragments.DashboardFragment
import com.example.moodguru.fragments.EmotionFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
var selectedFragment: Int = R.id.action_dashboard

class MainActivity : AppCompatActivity() {
    val TAG = "MainLog"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Log.d(TAG, "onCreate: MainActivity created")

        // Bottom Navigator on item click listener (dashboard/compose/chart)
        val fragmentManager = supportFragmentManager
        val btmNavi = findViewById<BottomNavigationView>(R.id.bottom_navigation)

        lateinit var fragmentToShow: Fragment

        btmNavi.setOnItemSelectedListener { item ->
            when (item.itemId){
                // dashboard button listener
                R.id.action_dashboard -> {
                    fragmentToShow = DashboardFragment()

                    if (selectedFragment == R.id.action_dashboard) {
                        Toast.makeText(this, "Refreshing the dashboard...", Toast.LENGTH_SHORT).show()
                        fragmentManager.commit {
                            replace(R.id.frgContainer, fragmentToShow)
                        }
                    }
                    else {
                        fragmentManager.commit {
                            setCustomAnimations(
                                R.anim.slide_in_left,
                                R.anim.slide_out_right,
                                R.anim.fade_in,
                                R.anim.fade_out
                            )
                            replace(R.id.frgContainer, fragmentToShow)
                            addToBackStack(null)
                        }
                    }

                    selectedFragment = R.id.action_dashboard
                }

                // compose button listener
                R.id.action_compose -> {
                    fragmentToShow = ComposeFragment()

                    if (selectedFragment == R.id.action_chart) {
                        fragmentManager.commit {
                            setCustomAnimations(
                                R.anim.slide_in_left,
                                R.anim.slide_out_right,
                                R.anim.fade_in,
                                R.anim.fade_out
                            )
                            replace(R.id.frgContainer, fragmentToShow)
                            addToBackStack(null)
                        }
                    }

                    if (selectedFragment == R.id.action_compose) {
                        fragmentManager.commit {
                            replace(R.id.frgContainer, fragmentToShow)
                        }
                    }

                    if (selectedFragment == R.id.action_dashboard) {
                        fragmentManager.commit {
                            setCustomAnimations(
                                R.anim.slide_in_right,
                                R.anim.slide_out_left,
                                R.anim.fade_in,
                                R.anim.fade_out
                            )
                            replace(R.id.frgContainer, fragmentToShow)
                            addToBackStack(null)
                        }
                    }

                    selectedFragment = R.id.action_compose
                }

                // chart button listener
                R.id.action_chart -> {
                    fragmentToShow = ChartFragment()

                    if (selectedFragment == R.id.action_chart) {
                        fragmentManager.commit {
                            replace(R.id.frgContainer, fragmentToShow)
                        }
                    }
                    else {
                        fragmentManager.commit {
                            setCustomAnimations(
                                R.anim.slide_in_right,
                                R.anim.slide_out_left,
                                R.anim.fade_in,
                                R.anim.fade_out
                            )
                            replace(R.id.frgContainer, fragmentToShow)
                            addToBackStack(null)
                        }
                    }

                    selectedFragment = R.id.action_chart
                }
                else -> fragmentToShow = ComposeFragment()
            }
//            fragmentManager.beginTransaction().replace(R.id.frgContainer, fragmentToShow).commit()


            // user interaction on the item has been handled
            true
        }

        // default
        btmNavi.selectedItemId = R.id.action_dashboard

        // if get signal from suggestion activity, land on dashboard
        if (intent.getStringExtra("signal").equals("from suggestion")){
            btmNavi.selectedItemId = R.id.action_dashboard
        }
    }
}
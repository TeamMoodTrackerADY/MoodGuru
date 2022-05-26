package com.example.moodguru

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import androidx.fragment.app.Fragment
import com.example.moodguru.fragments.ChartFragment
import com.example.moodguru.fragments.ComposeFragment
import com.example.moodguru.fragments.DashboardFragment
import com.example.moodguru.fragments.EmotionFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class ComposeActivity : AppCompatActivity() {
    companion object{
        val CONTINUE = "Continue"
        val SAVE = "Save"
        val BACK = "Back"
        val CANCEL = "Cancel"
        val TAG = "ComposeActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_compose)


        val fragmentManager = supportFragmentManager
        val btnPrev = findViewById<Button>(R.id.btnPrev)
        val btnNext = findViewById<Button>(R.id.btnNext)

        var fragmentToShow: Fragment = EmotionFragment()
        fragmentManager.beginTransaction().replace(R.id.frgContainer, fragmentToShow).commit()


        btnPrev.setOnClickListener {
            when(btnPrev.text){
                CANCEL -> {
                    Log.d(TAG, "cancel")
                    finish()
                }
                BACK ->{
                    fragmentToShow = EmotionFragment()
                    btnPrev.text = CANCEL
                    btnNext.text = CONTINUE
                    btnNext.visibility = View.VISIBLE
                }
            }
            fragmentManager.beginTransaction().replace(R.id.frgContainer, fragmentToShow).commit()
        }

        btnNext.setOnClickListener {
            when(btnNext.text){
                CONTINUE -> {
                    // TODO: send data from emotion to compose
                    fragmentToShow = ComposeFragment()
                    btnPrev.text = BACK
                    btnNext.text = SAVE
                    btnNext.visibility = View.GONE
                }
                SAVE -> {
                    // TODO: send data from composeFragment to Suggestion
                }

            }
            fragmentManager.beginTransaction().replace(R.id.frgContainer, fragmentToShow).commit()
        }



    }
}
package com.example.moodguru

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import com.example.moodguru.fragments.ChartFragment
import com.example.moodguru.fragments.ComposeFragment
import com.example.moodguru.fragments.DashboardFragment
import com.example.moodguru.fragments.EmotionFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class ComposeActivity : AppCompatActivity() {

    var emojiSelected = false;
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
                    overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
                }
                BACK ->{
                    fragmentToShow = EmotionFragment()
                    supportFragmentManager.commit {
                        setCustomAnimations(
                            R.anim.slide_in_left,
                            R.anim.slide_out_right,
                            R.anim.fade_in,
                            R.anim.fade_out
                        )
                        replace(R.id.frgContainer, fragmentToShow)
                        addToBackStack(null)
                    }
                    btnPrev.text = CANCEL
                    btnNext.text = CONTINUE
                    btnNext.visibility = View.VISIBLE
                }
            }
            //fragmentManager.beginTransaction().replace(R.id.frgContainer, fragmentToShow).commit()
        }

        btnNext.setOnClickListener {
            when(btnNext.text){
                CONTINUE -> {
                    // TODO: send data from emotion to compose
                    if (emojiSelected){
                        fragmentToShow = ComposeFragment()
                        supportFragmentManager.commit {
                            setCustomAnimations(
                                R.anim.slide_in_right,
                                R.anim.slide_out_left,
                                R.anim.fade_in,
                                R.anim.fade_out
                            )
                            replace(R.id.frgContainer, fragmentToShow)
                            addToBackStack(null)
                        }
                        btnPrev.text = BACK
                        btnNext.text = SAVE
                        btnNext.visibility = View.GONE
                    } else{
                        Toast.makeText(this, "Select an emoji to continue", Toast.LENGTH_SHORT)
                            .show()
                        emojiSelected = true
                    }

                }
                SAVE -> {
                    // TODO: send data from composeFragment to Suggestion
                }

            }
            //fragmentManager.beginTransaction().replace(R.id.frgContainer, fragmentToShow).commit()
        }



    }
}
package com.example.moodguru

import android.content.Context
import android.util.Log
import androidx.core.content.ContentProviderCompat.requireContext
import com.chaquo.python.Python
import com.chaquo.python.android.AndroidPlatform
import com.example.moodguru.fragments.ComposeFragment

class EmotionAnalyzer {
    companion object{
        fun analyze(text: String, context: Context): Float {
            if (! Python.isStarted()) {
                Python.start(AndroidPlatform(context))
            }
            val py = Python.getInstance()
            val module = py.getModule("__init__")
            val f = module.callAttr("analyze", text).toFloat()
            return f
        }
    }
}
package com.omurgun.moviesfromtmdb.util

import android.graphics.Color
import android.os.Build
import android.view.View
import android.view.Window

object Util {

    @Suppress("DEPRECATION")
    fun fullScreenActivity(window : Window){

        val decorView: View = window.decorView
        val uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
        decorView.systemUiVisibility = uiOptions

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.setDecorFitsSystemWindows(false)

        }
        else {
            //window.statusBarColor = Color.parseColor("#31CA92")
            window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN)
        }
        window.statusBarColor = Color.TRANSPARENT

    }
}
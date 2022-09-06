package com.app.compare_my_trade.ui.spalsh

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.app.compare_my_trade.MyBackgroundService
import com.app.compare_my_trade.R

import com.app.compare_my_trade.ui.postauthenticationui.HomeActivity
import com.app.compare_my_trade.utils.PreferenceUtils


class Splash : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        Handler(Looper.getMainLooper()).postDelayed({

            if (PreferenceUtils.getTokan(this) == null) {
                val intent = Intent(this,HomeActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_SINGLE_TOP
                startActivity(intent)
                PreferenceUtils.saveTokan(null,this)
            } else {
                val intent = Intent(this, HomeActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_SINGLE_TOP
                startActivity(intent)
            }
        }, 2000)
    }

    override fun onBackPressed() {

        super.onBackPressed()
        finishAffinity()

    }
    override fun onStart() {
        super.onStart()
        if (PreferenceUtils.getTokan(this) == null) {

        } else {
            val serviceIntent = Intent(this,
                MyBackgroundService::class.java)
            this@Splash.startService(serviceIntent)
        }

    }
}
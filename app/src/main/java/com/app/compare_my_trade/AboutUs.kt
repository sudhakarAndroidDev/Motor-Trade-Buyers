package com.app.compare_my_trade

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class AboutUs : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about_us)

        var back = findViewById(R.id.back) as TextView

        back.setOnClickListener {
            super.onBackPressed()
        }
    }
}
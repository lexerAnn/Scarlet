package com.android.lexter

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler

class SplashActivitys : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_activitys)
        Handler().postDelayed({
            var intent = Intent(this@SplashActivitys,EmptyActivity::class.java)
            startActivity(intent)

        }, 5500)
    }
}

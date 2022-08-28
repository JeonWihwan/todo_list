package com.goat.todolistapp.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.goat.todolistapp.R

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        Handler(Looper.getMainLooper()).postDelayed(Runnable {
            // Intent(현재 Activity, 이동할 Activity)
            startActivity(Intent(this, ListMainActivity::class.java))
            finish() // 현재 액티비티를 종료

        }, 1500)
    }
}
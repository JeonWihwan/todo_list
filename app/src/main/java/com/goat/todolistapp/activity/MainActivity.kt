package com.goat.todolistapp.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.goat.todolistapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() { // 앱컴액 상속 받아야됨.

    private lateinit var binding: ActivityMainBinding // lateinit => 처음엔 null이고 원하는 지점에서 초기화 가능

    override fun onCreate(savedInstanceState: Bundle?) { // 최초 실행시 초기화 되는 곳 onCreate
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.tvName.setText("안녕하세요 하하하하")

        // findViewById

        //setContentView(R.layout.activity_main)

        println("JWH onCreate !!!")
    }

    override fun onStart() {
        super.onStart()
        // 액티비티가 사용자에게 보여지기 직전에 호출 됨
        println("JWH onStart !!!")
    }

    override fun onResume() {
        super.onResume()
        // 액티비티가 사용자랑 상호작용 하기 직전에 호출 됨. (시작 or 재개 상태)
        println("JWH onResume !!!")
    }

    override fun onPause() {
        super.onPause()
        // 다음 액티비티가 보여지게 될 때 호출됨 (중지 상태)
        println("JWH onPause !!!")
    }

    override fun onStop() {
        super.onStop()
        // 액티비티가 사용자에게 완전히 보여지지 않을 때 호출됨.
        println("JWH onStop !!!")
    }

    override fun onDestroy() {
        super.onDestroy()
        // 액티비티가 제거(소멸)될 때 호출 됨.
        println("JWH onDestroy !!!")
    }

    override fun onRestart() {
        super.onRestart()
        // 액티비티가 멈췄다가 다시 시작되기 전에 호출 됨.
        println("JWH onRestart !!!")
    }
}
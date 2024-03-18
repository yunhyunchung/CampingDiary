package com.example.campingmedicine

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.campingmedicine.databinding.ActivityMainBinding
import com.kakao.sdk.common.util.Utility

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 카카오 로그인을 위한 해시키 값 구하기
        //val keyHash = Utility.getKeyHash(this)
        //Log.d("mobileApp", keyHash)

        // 로그인 담당 화면 intent 호출
        binding.btnStart.setOnClickListener {
            val intent = Intent(this, AuthActivity::class.java)

            // 호출하며 회원 상태 전달 => 회원 상태에 따른 auth 화면 구성하기
            if (binding.btnStart.text.equals("시작하기"))  // 1) 로그인 하길 원하는 로그아웃 상태
                intent.putExtra("data", "logout")  // 현재 상태 전달
            else if (binding.btnStart.text.equals("로그아웃"))
                intent.putExtra("data", "login")  // 2) 현재 로그인 상태임을 전달

            startActivity(intent)
        }
    }
}
package com.example.campingmedicine

import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.preference.PreferenceManager
import com.example.campingmedicine.databinding.ActivityHomeBinding

class HomeActivity : AppCompatActivity() {
    lateinit var binding: ActivityHomeBinding
    lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // setContentView(R.layout.activity_home)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        // 액션바 내용을 툴바에 적용
        setSupportActionBar(binding.toolbar)

        // 설정 xml의 설정값 가져오기 (배경색,글자색 설정값 가져와서 acitivity에 적용)
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
        val bgColor = sharedPreferences.getString("color", "#ffffff")
        binding.rootLayout.setBackgroundColor(Color.parseColor(bgColor))

        /*val textColor = sharedPreferences.getString("textColor", "#000000")
        binding.run {
            userEmailTv.setTextColor(Color.parseColor(textColor))
            textView1.setTextColor(Color.parseColor(textColor))
            textView2.setTextColor(Color.parseColor(textColor))
        }*/

        // 로그인 담당 화면 intent 호출
        binding.btnLogin.setOnClickListener {
            val intent = Intent(this, AuthActivity::class.java)

            // 호출하며 회원 상태 전달 => 회원 상태에 따른 auth 화면 구성하기
            if(binding.btnLogin.text.equals("로그인"))  // 1) 로그인 하길 원하는 로그아웃 상태
                intent.putExtra("data", "logout")   // 현재 상태 전달
            else if (binding.btnLogin.text.equals("로그아웃"))
                intent.putExtra("data", "login")   // 2) 현재 로그인 상태임을 전달

            startActivity(intent)
        }
    }

    override fun onStart() {  // 로그인을 끝내고 홈 화면으로 다시 돌아왔을 때
        super.onStart()
        // Firebase 이메일 로그인 or Kakao 로그인 했으면
        if (MyApplication.checkAuth() || MyApplication.email != null) {  // 이메일 인증 || 이메일 정보 받기 성공
            binding.btnLogin.text = "로그아웃"
            binding.userEmailTv.text = "안녕하세요, ${MyApplication.email}님!"
            binding.userEmailTv.textSize = 18F

            binding.campBtn.setOnClickListener {    // 캠핑장 찾기
                val intent = Intent(this, CampFindActivity::class.java)
                startActivity(intent)
            }

            binding.diaryBtn.setOnClickListener {   // 캠핑 일지 쓰기
                val intent = Intent(this, DiaryActivity::class.java)
                startActivity(intent)
            }
            binding.profileChangeBtn.setOnClickListener {  // 프로필 사진 바꾸기
                val intent = Intent(this, ProfileActivity::class.java)
                startActivity(intent)
            }
        }
        else {  // 로그아웃 상태면
            binding.btnLogin.text = "로그인"
            binding.userEmailTv.text = "안녕하세요, 캠핑 다이어리입니다!"
            binding.userEmailTv.textSize = 18F
        }
    }

    override fun onResume() {   // activity가 중단됐다가 다시 실행될 때 호출됨
        super.onResume()        // 배경색 설정 변경하고 다시 activity 실행했을 때 onResume() 호출
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
        val bgColor = sharedPreferences.getString("color", "#ffffff")
        binding.rootLayout.setBackgroundColor(Color.parseColor(bgColor))

        val textColor = sharedPreferences.getString("textColor", "#000000")
        binding.run {
            userEmailTv.setTextColor(Color.parseColor(textColor))
            textView1.setTextColor(Color.parseColor(textColor))
            textView2.setTextColor(Color.parseColor(textColor))
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)    // menu_main.xml을 메뉴로 사용
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menu_main_setting) {    // 액션바의 설정 버튼을 누르면 설정 화면으로 이동
            val intent = Intent(this, SettingActivity::class.java)
            startActivity(intent)
        }
        return super.onOptionsItemSelected(item)
    }

}
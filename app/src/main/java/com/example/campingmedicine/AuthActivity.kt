package com.example.campingmedicine

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.example.campingmedicine.databinding.ActivityAuthBinding
import com.example.campingmedicine.databinding.ActivityMainBinding
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.user.UserApiClient

class AuthActivity : AppCompatActivity() {
    lateinit var binding: ActivityAuthBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_auth)
        binding = ActivityAuthBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // MainActivity가 전달하는 회원 상태 mode를 받음 => mode별 auth 화면 뷰 가시성 조절
        changeVisibility(intent.getStringExtra("data").toString())

        binding.goSignInBtn.setOnClickListener {   // 3) 회원가입 버튼을 누르면 회원가입 화면으로 뷰 구성
            changeVisibility("signin")
        }
        binding.signBtn.setOnClickListener {   // 4) 가입 버튼을 누르면 이메일 인증 메일 보내기
            val email = binding.authEmailEditView.text.toString()
            val password = binding.authPasswordEditView.text.toString()
            MyApplication.auth.createUserWithEmailAndPassword(email, password)  // 입력한 이메일, 비밀번호로 회원가입
                .addOnCompleteListener(this) { task ->
                    binding.authEmailEditView.text.clear()  // 입력한 내용 지우기
                    binding.authPasswordEditView.text.clear()

                    if (task.isSuccessful) {  // 회원가입 성공하면
                        // 사용자에게 인증 메일 보내기 (이메일 검증)
                        MyApplication.auth.currentUser?.sendEmailVerification()
                            ?.addOnCompleteListener { sendTask ->
                                if (sendTask.isSuccessful) {    // 회원가입 & 메일 전송 성공
                                    Toast.makeText(baseContext, "회원가입에 성공했습니다. 전송된 메일을 확인해주세요.", Toast.LENGTH_SHORT).show()
                                    changeVisibility("logout")      // 로그아웃 상태 = 회원가입만 하고 로그인은 안 한 상태 (메일 인증 전 상태)
                                } else {   // 메일 전송 실패
                                    Toast.makeText(baseContext, "메일 발송 실패", Toast.LENGTH_SHORT).show()
                                    changeVisibility("logout")     // 현재 로그아웃 상태에 맞는 화면 출력
                                }
                            }
                    } else {   // 회원가입 실패하면
                        Toast.makeText(baseContext, "회원가입 실패", Toast.LENGTH_SHORT).show()  // 리스너 안에 있어서 this 대신 baseContext
                        changeVisibility("logout")  // 현재 로그아웃 상태에 맞는 화면 출력
                    }
                }
        }
        binding.loginBtn.setOnClickListener {   // 사용자가 입력한 이메일, 비밀번호 로그인
            val email = binding.authEmailEditView.text.toString()
            val password = binding.authPasswordEditView.text.toString()
            MyApplication.auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    binding.authEmailEditView.text.clear()
                    binding.authPasswordEditView.text.clear()

                    if (task.isSuccessful) {  // 로그인 성공하면
                        if (MyApplication.checkAuth()) {   // 이메일 인증했는지 확인 (인증 메일 눌렀는지)
                            MyApplication.email = email    //  인증했으면 로그인 성공
                            // changeVisibility("login")
                            startActivity(Intent(this, HomeActivity::class.java))  // 홈 화면으로
                            Toast.makeText(baseContext, "로그인 성공", Toast.LENGTH_SHORT).show()
                        }
                        else {   // 이메일 인증 실패
                            Toast.makeText(baseContext, "전송된 메일로 이메일 인증이 되지 않았습니다.", Toast.LENGTH_SHORT).show()
                        }
                    } else {
                        Toast.makeText(baseContext, "로그인 실패", Toast.LENGTH_SHORT).show()
                    }
                }
        }
        binding.logoutBtn.setOnClickListener {
            // Firebase 로그아웃
            MyApplication.auth.signOut()
            MyApplication.email = null

            // Kakao 로그아웃
            UserApiClient.instance.logout { error ->
                if (error != null) {
                    Toast.makeText(baseContext, "로그아웃 실패", Toast.LENGTH_SHORT).show()
                }
                else {
                    Toast.makeText(baseContext, "로그아웃 성공", Toast.LENGTH_SHORT).show()
                }
            }
            // finish()  // MainActivity로 돌아가기
            startActivity(Intent(this, HomeActivity::class.java))  // 홈 화면으로 돌아가기

            //changeVisibility("logout")
            finish()  // MainActivity로 돌아가기
        }

        binding.btnKakaoLogin.setOnClickListener {   // 카카오 로그인하기
            // 1) 로그인 상태인지 먼저 확인 : 토큰 정보 보기
            UserApiClient.instance.accessTokenInfo { tokenInfo, error ->
                if (error != null) {
                    Log.e("mobileApp", "토큰 정보 보기 실패", error)
                }
                else if (tokenInfo != null) {
                    Log.i("mobileApp", "토큰 정보 보기 성공")  // 로그인 상태
                    startActivity(Intent(this, HomeActivity::class.java))  // 홈 화면으로 돌아가기
                }
            }
            // 2) 카카오계정으로 로그인 공통 callback 구성
            // 카카오톡으로 로그인 할 수 없어 카카오계정으로 로그인할 경우 사용됨
            val callback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
                if (error != null) {
                    Log.e("mobileApp", "카카오계정으로 로그인 실패", error)
                }
                else if (token != null) {
                    Log.i("mobileApp", "카카오계정으로 로그인 성공 ${token.accessToken}")
                    // 로그인 성공하면 사용자 정보 요청 (카카오 계정 이메일 가져오기)
                    UserApiClient.instance.me { user, error ->
                        if (error != null) {
                            Log.e("mobileApp", "사용자 정보 요청 실패", error)
                        }
                        else if (user != null) {
                            Log.i("mobileApp", "사용자 정보 요청 성공 ${user.kakaoAccount?.email}")
                            var scopes = mutableListOf<String>()
                            if(user.kakaoAccount?.email != null) {
                                MyApplication.email = user.kakaoAccount?.email.toString()
                                startActivity(Intent(this, HomeActivity::class.java))  // 홈 화면으로 돌아가기
                            }
                            else if (user.kakaoAccount?.emailNeedsAgreement == true) {
                                Log.i("mobileApp", "사용자에게 추가 동의를 받아야 합니다.")
                                scopes.add("account_email")  // 이메일에 사용자 추가 동의 필요
                                UserApiClient.instance.loginWithNewScopes(this, scopes) { token, error ->
                                    if (error != null) {
                                        Log.e("mobileApp", "사용자 추가 동의 실패", error)
                                    }
                                    else {   // 사용자 추가 동의 필요 => 사용자 정보 재요청
                                        UserApiClient.instance.me { user, error ->
                                            if (error != null) {
                                                Log.e("mobileApp", "사용자 정보 요청 실패", error)
                                            }
                                            else if(user != null) {
                                                MyApplication.email = user.kakaoAccount?.email.toString()
                                                startActivity(Intent(this, HomeActivity::class.java))  // 홈 화면으로 돌아가기
                                            }
                                        }

                                    }
                                }
                            }
                            else {  // 이메일 획득 불가
                                Log.e("mobileApp", "이메일 획득 불가")
                            }
                        }
                    }
                }
            }
            // 3) callback 함수를 이용하여 실제로 카카오톡/계정 로그인하기
            // 카카오톡이 설치되어 있으면 카카오톡으로 로그인, 아니면 카카오계정으로 로그인
            if(UserApiClient.instance.isKakaoTalkLoginAvailable(this)) {
                UserApiClient.instance.loginWithKakaoTalk(this, callback = callback)
            } else {
                UserApiClient.instance.loginWithKakaoAccount(this, callback = callback)
            }

        }
    }

    fun changeVisibility(mode: String) {  // 회원상태 mode별 layout view 객체 visibility 조절
        if (mode.equals("login")) {   // 로그인 상태면 로그아웃 안내 text와 버튼만 보이기
            binding.run {
                authMainTextView.text = "정말 로그아웃 하시겠습니까?"
                authMainTextView.visibility = View.VISIBLE
                logoutBtn.visibility = View.VISIBLE

                goSignInBtn.visibility = View.GONE
                authEmailEditView.visibility = View.GONE
                authPasswordEditView.visibility = View.GONE
                signBtn.visibility = View.GONE
                loginBtn.visibility = View.GONE

                btnKakaoLogin.visibility = View.GONE
            }
        }
        else if(mode.equals("logout")) {   // 로그아웃 상태면 로그인/회원가입 보이기
            binding.run {
                authMainTextView.text = "로그인 하거나 회원가입 해주세요."
                authMainTextView.visibility = View.VISIBLE
                logoutBtn.visibility = View.GONE
                goSignInBtn.visibility = View.VISIBLE
                authEmailEditView.visibility = View.VISIBLE
                authPasswordEditView.visibility = View.VISIBLE
                signBtn.visibility = View.GONE
                loginBtn.visibility = View.VISIBLE

                btnKakaoLogin.visibility = View.VISIBLE
            }
        }
        else if (mode.equals("signin")) {   // 회원가입하고 있는 상태면 이메일, 비밀번호 입력해서 가입 버튼 누르기
            binding.run {
                authMainTextView.visibility = View.GONE
                logoutBtn.visibility = View.GONE
                goSignInBtn.visibility = View.GONE
                authEmailEditView.visibility = View.VISIBLE
                authPasswordEditView.visibility = View.VISIBLE
                signBtn.visibility = View.VISIBLE
                loginBtn.visibility = View.GONE

                btnKakaoLogin.visibility = View.GONE
            }
        }
    }
}
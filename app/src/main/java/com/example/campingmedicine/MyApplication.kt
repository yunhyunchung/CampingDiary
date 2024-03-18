package com.example.campingmedicine

import androidx.multidex.MultiDexApplication
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage
import com.kakao.sdk.common.KakaoSdk
import com.tickaroo.tikxml.TikXml
import com.tickaroo.tikxml.retrofit.TikXmlConverterFactory
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MyApplication: MultiDexApplication() {
    companion object {
        // 파이어베이스 로그인
        lateinit var auth: FirebaseAuth  // 파이어베이스 인증 객체
        var email: String? = null

        // 이미지, 글 공유하는 firebase
        lateinit var db: FirebaseFirestore      // 일반데이터 (글) 저장
        lateinit var storage: FirebaseStorage   // 사진, 이미지 저장

        fun checkAuth(): Boolean {  // // 이메일 인증 체크(이메일로 인증했는지 아닌지)
            val currentUser = auth.currentUser
            return currentUser?.let {
                email = currentUser.email
                currentUser.isEmailVerified  // 이메일 인증했으면 true
            } ?: let {
                false  // currentUser가 null이면
            }
        }
        // Json Retrofit 객체 생성
        var networkService : NetworkService     // json 이용
        val retrofit : Retrofit
            get() = Retrofit.Builder()
                .baseUrl("http://api.visitkorea.or.kr/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()

        // Xml Retrofit 객체 생성
        var networkServiceXml : NetworkService  // xml 사용
        val parser = TikXml.Builder().exceptionOnUnreadXml(false).build()
        val retrofitXml : Retrofit
            get() = Retrofit.Builder()
                .baseUrl("http://api.visitkorea.or.kr/")
                .addConverterFactory(TikXmlConverterFactory.create(parser))  // 필요한 정보만 추출 => parser 사용
                .build()

        init {          // Retrofit으로 통신 서비스 인터페이스를 구현한 네트워크 서비스 객체 생성
            networkService = retrofit.create(NetworkService::class.java)
            networkServiceXml = retrofitXml.create(NetworkService::class.java)
        }
    }

    override fun onCreate() {
        super.onCreate()
        // Firebase auth 초기화
        auth = Firebase.auth

        // 파이어스토어, 스토리지 초기화
        db = FirebaseFirestore.getInstance()
        storage = Firebase.storage

        // Kakao SDK 초기화 (=> Kakao Login 이용)
        KakaoSdk.init(this, "39ff797f55c22f562798583af457d481")  // 카카오 네이티브 앱 키
    }
}
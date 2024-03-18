package com.example.campingmedicine

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.campingmedicine.databinding.ActivityDiaryBinding

class DiaryActivity : AppCompatActivity() {
    lateinit var binding : ActivityDiaryBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_diary)
        binding = ActivityDiaryBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar3)

        // 외부 저장소 읽기 권한 확인, 요청
        myCheckPermission(this)

        // 로그인 인증됐으면 데이터 추가화면 실행
        binding.addFab.setOnClickListener {
            if(MyApplication.checkAuth()) {
                startActivity(Intent(this, AddActivity::class.java))
            }
            else {
                Toast.makeText(this, "인증을 먼저 진행해주세요", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onStart() {    // 나갔다가 다시 돌아왔을 때
        super.onStart()
        if (MyApplication.checkAuth() || MyApplication.email != null) {   // 인증된 이메일이면 (로그인이 됐으면)
            binding.mainRecyclerView.visibility = View.VISIBLE
            makeRecyclerView()
        }
        else {      // 로그아웃 상태면
            binding.mainRecyclerView.visibility = View.GONE
        }
    }

    private fun makeRecyclerView() {     // 파이어스토어에 저장된 컬렉션(데이터)를 모두 가져와서 리사이클러뷰 리스트에 출력
        MyApplication.db.collection("news")
            .get()                      // news collection 모두 가져오기
            .addOnSuccessListener { result ->
                val itemList = mutableListOf<ItemData>()
                for (document in result) {
                    val item = document.toObject(ItemData::class.java)
                    item.docId = document.id
                    itemList.add(item)
                }
                binding.mainRecyclerView.layoutManager = LinearLayoutManager(this)
                binding.mainRecyclerView.adapter = MyAdapterFirestore(this, itemList)
            }
            .addOnFailureListener {
                Toast.makeText(this, "서버로부터 데이터 획득 실패", Toast.LENGTH_SHORT).show()
            }
    }

}
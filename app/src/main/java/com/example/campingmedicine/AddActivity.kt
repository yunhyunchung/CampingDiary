package com.example.campingmedicine

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.campingmedicine.databinding.ActivityAddBinding
import java.io.File
import java.util.*

class AddActivity : AppCompatActivity() {
    lateinit var binding : ActivityAddBinding
    lateinit var filePath : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_add)
        binding = ActivityAddBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar4)
    }

    // 메뉴의 이미지 선택, 저장 버튼 적용
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_add, menu)     // 메뉴 xml을 액티비티에 적용
        return super.onCreateOptionsMenu(menu)
    }

    // 갤러리에서 선택한 사진을 그 전 화면 이미지 뷰에 출력
    val requestLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        if (it.resultCode === android.app.Activity.RESULT_OK) {   // 갤러리에서 선택한 사진을 그 전 화면 이미지 뷰에 출력
            Glide
                .with(applicationContext)
                .load(it.data?.data)   // 이미지 위치 정보 가져옴
                .apply(RequestOptions().override(250,200))   // (250,200) 크기로 이미지 불러오기
                .centerCrop()    // center를 중심으로 이미지 자름
                .into(binding.addImageView)   // layout xml에 선택한 이미지 출력

            val cursor = contentResolver.query(it.data?.data as Uri,   // 이미지 정보 data 가져옴
                arrayOf<String>(MediaStore.Images.Media.DATA), null, null, null)
            cursor?.moveToFirst().let {
                filePath = cursor?.getString(0) as String    // 이미지 data 중 맨 앞의 이미지 경로 가져오기
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId === R.id.menu_add_gallery) {    // 갤러리 앱을 실행해서 모든 이미지 가져오기
            val intent = Intent(Intent.ACTION_PICK)
            intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*")
            requestLauncher.launch(intent)
        }
        else if (item.itemId === R.id.menu_add_save) {   // 사용자가 입력한 사진, 글이 모두 있으면 저장
            if (binding.addImageView.drawable !== null && binding.addEditView.text.isNotEmpty()) {
                saveStore()
            }
            else {
                Toast.makeText(this, "데이터가 모두 입력되지 않았습니다.", Toast.LENGTH_SHORT).show()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    // 글과 이미지 등록하고 파이어스토어에 저장 -> 스토리지에 이미지 업로드
    private fun saveStore() {
        val data = mapOf(
            "email" to MyApplication.email,  // 로그인(인증)한 사용자로 파이어베이스에 읽기,쓰기 권한 획득
            "content" to binding.addEditView.text.toString(),   // 사용자가 입력한 글
            "date" to dateToString(Date()),   // 데이터가 등록된 시간 (현재 시간)
        )
        MyApplication.db.collection("news")   // 저장할 firestore db 이름
            .add(data)
            .addOnSuccessListener {
                uploadImage(it.id)      // 스토리지에 데이터 저장 후 데이터 id값으로 스토리지에 이미지 업로드
            }
            .addOnFailureListener {
                Log.d("mobileApp", "data save error")
            }
    }
    private fun uploadImage(docId : String) {   // 이미지는 스토리지에 저장
        val storage = MyApplication.storage
        val storageRef = storage.reference    // 스토리지 참조하는 객체 생성
        val imageRef = storageRef.child("images/${docId}.jpg")  // 실제 업로드하는 파일(경로)을 참조하는 객체 생성

        // 파일 업로드
        val file = Uri.fromFile(File(filePath))
        imageRef.putFile(file)   // 지정한 파일 경로에 이미지 업로드(저장)
            .addOnSuccessListener {
                Toast.makeText(this, "save complete!", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener {
                Log.d("mobileApp", "file save error")
            }
    }

}
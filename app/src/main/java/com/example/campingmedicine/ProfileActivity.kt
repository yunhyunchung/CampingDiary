package com.example.campingmedicine

import android.R.attr
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import com.example.campingmedicine.databinding.ActivityProfileBinding
import java.io.ByteArrayOutputStream
import java.io.File
import java.text.SimpleDateFormat
import java.util.*


class ProfileActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        lateinit var filePath : String

        super.onCreate(savedInstanceState)
        // setContentView(R.layout.activity_profile)
        val binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //val binding2 = ActivityHomeBinding.inflate(layoutInflater)
        //setContentView(binding2.root)

        // 갤러리 요청 런처 - 갤러리에서 사진을 하나 선택했을 때 그 사진을 갖고 다시 앱으로 돌아왔을 때 실행
        val requestGalleryLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            try {
                // inSampleSize 불러올 이미지 크기 비율 계산, 지정
                val calRatio = calculateInSampleSize(
                    it.data!!.data!!, 150,150
                )
                // 옵션을 지정해 비트맵 생성
                val option = BitmapFactory.Options()
                // option.inSampleSize = 4

                // 이미지 로딩
                var inputStream = contentResolver.openInputStream(it.data!!.data!!)
                val bitmap = BitmapFactory.decodeStream(inputStream, null, option)  // 선택한 inputStream 이미지를 bitmap에 담기
                inputStream!!.close()
                inputStream = null

                bitmap.let {    // 이미지를 가져왔으면
                    binding.userIdImg.setImageBitmap(bitmap)
                } ?: let{       // 가져온 이미지가 없으면
                    Log.d("mobileApp", "bitmap null")
                }
            } catch(e:Exception) {
                e.printStackTrace()
            }
        }

        // 갤러리 앱 실행 인텐트 호출 => 사진 목록 출력
        binding.galleryBtn.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            intent.type = "image/*"
            requestGalleryLauncher.launch(intent)
        }

        // 카메라 앱 연동 - 사진 파일을 공유하는 방법

        // 카메라 요청 런처 - 사진 촬영 후 다시 앱으로 돌아왔을 때 실행. 찍은 사진을 비트맵 이미지로 생성 & 앱에 출력
        val requestCameraFileLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            val calRatio = calculateInSampleSize(Uri.fromFile(File(filePath)), 150, 150)
            val option = BitmapFactory.Options()
            option.inSampleSize = calRatio

            val bitmap = BitmapFactory.decodeFile(filePath, option)   // 이미지 파일 경로 전달해서 비트맵 객체 얻음
            bitmap?.let {
                binding.userIdImg.setImageBitmap(bitmap)    // 이미지가 있으면 화면에 출력
                //binding2.userIdImg.setImageBitmap(bitmap)
            } ?: let {
                Log.d("mobileApp", "이미지가 없습니다. 다시 시도해주세요.")
            }
        }
        val timeStamp : String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())     // 현재 날짜, 시각을 파일명으로 지정
        val storeDir : File? = getExternalFilesDir(Environment.DIRECTORY_PICTURES)             // 찍은 사진을 저장할 위치(디렉토리)
        val file  = File.createTempFile("JPEG_${timeStamp}_", ".jpg", storeDir)     // 찍은 사진을 저장할 파일 생성
        filePath = file.absolutePath

        val photoURI : Uri = FileProvider.getUriForFile(    // 만든 사진 파일의 URI 가져오기
            this,
            "com.example.campingmedicine.fileprovider",
            file
        )

        // FileProvider로 만든 파일 경로를 가지고 카메라 앱 실행
        binding.cameraBtn2.setOnClickListener {
            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            intent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
            requestCameraFileLauncher.launch(intent)
        }

    }

    // 실제 화면에 출력되는 크기와 비교해서 적절한 비율로 이미지 크기 줄이는 로직
    private fun calculateInSampleSize(fileUri: Uri, reqWidth: Int, reqHeight: Int): Int {
        val options = BitmapFactory.Options()
        options.inJustDecodeBounds = true
        try {
            var inputStream = contentResolver.openInputStream(fileUri)

            //inJustDecodeBounds 값을 true 로 설정한 상태에서 decodeXXX() 를 호출.
            //로딩 하고자 하는 이미지의 각종 정보가 options 에 설정 된다.
            BitmapFactory.decodeStream(inputStream, null, options)
            inputStream!!.close()
            inputStream = null
        } catch (e: Exception) {
            e.printStackTrace()
        }
        //비율 계산........................
        val (height: Int, width: Int) = options.run { outHeight to outWidth }
        var inSampleSize = 1
        //inSampleSize 비율 계산
        if (height > reqHeight || width > reqWidth) {

            val halfHeight: Int = height / 2
            val halfWidth: Int = width / 2

            while (halfHeight / inSampleSize >= reqHeight && halfWidth / inSampleSize >= reqWidth) {
                inSampleSize *= 2
            }
        }
        return inSampleSize
    }
}
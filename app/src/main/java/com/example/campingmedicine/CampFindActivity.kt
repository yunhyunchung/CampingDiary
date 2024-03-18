package com.example.campingmedicine

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.campingmedicine.databinding.ActivityCampFindBinding

class CampFindActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_camp_find)
        val binding = ActivityCampFindBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar2)       // 액션바 내용을 툴바에 적용

        val fragment = RetrofitFragment()
        val xmlfragment = XmlFragment()
        val bundle = Bundle()  // fragment에 전달할 데이터

        binding.searchBtn.setOnClickListener {
            when(binding.rGroup.checkedRadioButtonId) {
                //R.id.json -> bundle.putString("returnType", "json")
                R.id.xml -> bundle.putString("returnType", "xml")
                else -> bundle.putString("returnType", "json")  // default
            }
            /*if(binding.rGroup.checkedRadioButtonId == R.id.json) {
                fragment.arguments = bundle  // fragment 인수에 데이터 전달
                supportFragmentManager.beginTransaction()  // Retrofit Fragment를 layout xml에 만든 FragmentLayout에 출력
                    .replace(R.id.activity_content, fragment)
                    .commit()
            }*/
            if(binding.rGroup.checkedRadioButtonId == R.id.xml){
                xmlfragment.arguments = bundle  // fragment 인수에 데이터 전달
                supportFragmentManager.beginTransaction()  // Retrofit Fragment를 layout xml에 만든 FragmentLayout에 출력
                    .replace(R.id.activity_content, xmlfragment)
                    .commit()
            }
        }

    }
}
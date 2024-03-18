package com.example.campingmedicine

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.campingmedicine.databinding.ItemMainBinding

class XmlViewHolder(val binding: ItemMainBinding) : RecyclerView.ViewHolder(binding.root)

class XmlAdapter(val context: Context, val datas: MutableList<myItem>?):  // ItemXmlModel.kt 의 myItem 클래스에 실질 데이터!
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun getItemCount(): Int {
        return datas?.size ?: 0
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return XmlViewHolder(ItemMainBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {  // viewholder에 data 결합
        val binding = (holder as XmlViewHolder).binding
        val model = datas!![position]

        Glide.with(context)
            .load(model.firstImageUrl)
            .into(binding.firstImage)
        binding.campName.text = model.facltNm
        binding.lineIntro.text = "한 줄 소개: ${model.lineIntro}"
        binding.feature.text = "특징: ${model.featureNm}"
        binding.address1.text = "주소: ${model.addr1}"
        binding.induty.text = "업종: ${model.induty}"
        binding.tel.text = "전화번호: ${model.tel}"
        binding.homepage.text = "홈페이지: ${model.homepage}"
        binding.resveUrl.text = "예약 페이지: ${model.resveUrl}"
        binding.resveCl.text = "예약 구분: ${model.resveCl}"
    }
}
package com.example.campingmedicine

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.campingmedicine.databinding.ItemMainBinding

class MyViewHolder(val binding: ItemMainBinding) : RecyclerView.ViewHolder(binding.root)

class MyAdapter (val context: Context, val datas: MutableList<ItemModel>?) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun getItemCount(): Int {
        Log.d("mobileApp", "${datas?.size}")
        return datas?.size ?: 0
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return MyViewHolder(ItemMainBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }
    // 서버에서 받은 데이터를 layout에 출력
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val binding = (holder as MyViewHolder).binding
        val model = datas!![position]

        binding.campName.text = model.facltNm
        binding.lineIntro.text = model.lineIntro
        binding.feature.text = model.featureNm
        binding.address1.text = model.addr1
        Glide.with(context)
            .load(model.firstImageUrl)
            .into(binding.firstImage)
    }
}
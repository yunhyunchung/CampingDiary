package com.example.campingmedicine

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.campingmedicine.databinding.ItemFirestoreBinding

// 사용자가 올린 이미지와 글을 담는 뷰 홀더에 데이터를 연결함
class MyViewHolderFirestore(val binding: ItemFirestoreBinding) : RecyclerView.ViewHolder(binding.root)

class MyAdapterFirestore(val context: Context, val itemList: MutableList<ItemData>) : RecyclerView.Adapter<MyViewHolderFirestore>() {
    override fun getItemCount(): Int {
        return itemList.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolderFirestore {
        val layoutInflater = LayoutInflater.from(parent.context)
        return MyViewHolderFirestore(ItemFirestoreBinding.inflate(layoutInflater))
    }

    override fun onBindViewHolder(holder: MyViewHolderFirestore, position: Int) {
        val data = itemList.get(position)
        holder.binding.run {
            itemEmailView.text = data.email
            itemDateView.text = data.date
            itemContentView.text = data.content
        }
        // 이미지가 저장된 스토리지에서 이미지 내려받기
        val imageRef = MyApplication.storage.reference.child("images/${data.docId}.jpg")
        imageRef.downloadUrl.addOnCompleteListener { task ->    // 다운로드한 이미지
            if (task.isSuccessful) {
                Glide.with(context)
                    .load(task.result)      // 다운로드한 이미지 경로
                    .into(holder.binding.itemImageView)     // 뷰 홀더의 이미지 뷰에 출력
            }

        }
    }
}
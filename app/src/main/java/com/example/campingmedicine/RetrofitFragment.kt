package com.example.campingmedicine

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.campingmedicine.databinding.FragmentRetrofitBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [RetrofitFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class RetrofitFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    lateinit var binding: FragmentRetrofitBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // fragment view binding
        binding = FragmentRetrofitBinding.inflate(inflater, container, false)
        //val returnType = arguments?.getString("_type")
        lateinit var adapter : MyAdapter

        // 네트워크 통신 준비
        val call: Call<PageListModel> = MyApplication.networkService.getList(
            "zQwFKfgI62/elDZfgyzVhzuiTiNrGvihxoAFPNCVMxXXlg0HroWK23czsHnn1IRzXHhy4cvMS/m9jBnczxsPAQ==",
            1,
            10,
            "ETC",
            "AppTest",
            128.6142847,
            36.0345423,
            2000,
            "json",
        )

        // 네트워크 통신 수행
        call?.enqueue(object: Callback<PageListModel> {
            override fun onResponse(call: Call<PageListModel>, response: Response<PageListModel>) {
                if (response.isSuccessful) {    // 통신 성공 (code = 200)
                    Log.d("mobileApp", "$response")
                    // 서버에서 넘어온 전국 캠핑장 데이터를 리사이클러 뷰로 출력
                    binding.retrofitRecyclerView.layoutManager = LinearLayoutManager(activity)

                    adapter = MyAdapter(activity as Context, response.body()?.data)
                    binding.retrofitRecyclerView.adapter = adapter

                    //adapter.notifyDataSetChanged()      // 목록 화면 다시 출력
                    binding.retrofitRecyclerView.addItemDecoration(
                        DividerItemDecoration(activity as Context, LinearLayoutManager.VERTICAL)
                    )

                }
            }
            override fun onFailure(call: Call<PageListModel>, t: Throwable) {   // 통신 실패
                Log.d("mobileApp", "Error: $t")
            }
        })
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment RetrofitFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            RetrofitFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
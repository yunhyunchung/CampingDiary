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
import com.example.campingmedicine.databinding.FragmentXmlBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [XmlFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class XmlFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding = FragmentXmlBinding.inflate(inflater, container, false)

        // 네트워크 통신 준비
        val returnType = arguments?.getString("returnType")
        val call: Call<responseInfo> = MyApplication.networkServiceXml.getXmlList(  // 네트워크 서비스 인터페이스 함수 호출 -> Call 객체 반환
            "zQwFKfgI62/elDZfgyzVhzuiTiNrGvihxoAFPNCVMxXXlg0HroWK23czsHnn1IRzXHhy4cvMS/m9jBnczxsPAQ==",
            1,
            10,
            "ETC",
            "AppTest",
        )
        // 네트워크 통신 수행
        call?.enqueue(object: Callback<responseInfo> {
            override fun onResponse(call: Call<responseInfo>, response: Response<responseInfo>) {
                if(response.isSuccessful) {   // 통신 성공 (code = 200)
                    Log.d("mobileApp", "$response")
                    binding.xmlRecyclerView.layoutManager = LinearLayoutManager(activity)
                    binding.xmlRecyclerView.adapter = XmlAdapter(activity as Context, response.body()!!.body!!.items!!.item)  // ItemXmlModel.kt의 MutableList<myItem>
                    binding.xmlRecyclerView.addItemDecoration(DividerItemDecoration(activity as Context, LinearLayoutManager.VERTICAL))
                    // response.body(): 서버에서 넘어온 데이터를 가져와 adapter에 전달
                }
            }
            override fun onFailure(call: Call<responseInfo>, t: Throwable) {  // 통신 실패
                Log.d("mobileApp", "onFailure $t")
            }
        })
        return binding.root
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment XmlFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            XmlFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
package com.example.campingmedicine

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface NetworkService {  // 네트워크 통신 필요할 때 호출할 함수 정의 (서비스 요청)
    // JSON
    @GET("openapi/service/rest/GoCamping/basedList")
    fun getList (
        @Query("serviceKey") serviceKey: String?,
        @Query("pageNo") pageNo: Int,
        @Query("numOfRows") numOfRows: Int,
        @Query("MobileOS") MobileOS: String?,
        @Query("MobileApp") MobileApp: String?,
        @Query("mapX") mapX: Double,
        @Query("mapY") mapY: Double,
        @Query("radius") radius: Long,
        @Query("_type") _type: String?,   // _type=json
    ) : Call<PageListModel>

    // XML
    @GET("openapi/service/rest/GoCamping/basedList")
    fun getXmlList(
        @Query("serviceKey") serviceKey: String?,
        @Query("pageNo") pageNo: Int,
        @Query("numOfRows") numOfRows: Int,
        @Query("MobileOS") MobileOS: String?,
        @Query("MobileApp") MobileApp: String?,
    ): Call<responseInfo>  // ItemXmlModel.kt 의 responseInfo 클래스 타입 (Call 객체)
}
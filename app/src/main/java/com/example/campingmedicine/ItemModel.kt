package com.example.campingmedicine

data class ItemModel(
    val firstImageUrl: String?,     // 대표 이미지 url
    val facltNm: String,        // 캠핑장 이름
    val lineIntro: String,
    val featureNm: String,
    val addr1: String,      // 주소
    /*
    val intro: String,
    val mapX: Double,
    val mapY: Double,
    val tel: String,
    val homepage: String,
    val resveUrl: String,   // 예약 페이지
    val resveCl: String,    // 예약 구분
    val posblFcltyCl: String,   // 주변이용가능시설
     */
)

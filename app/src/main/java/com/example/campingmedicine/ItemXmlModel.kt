package com.example.campingmedicine

import com.tickaroo.tikxml.annotation.Element
import com.tickaroo.tikxml.annotation.PropertyElement
import com.tickaroo.tikxml.annotation.Xml

@Xml(name = "response")
data class responseInfo(
    @Element
    val header : Header,
    @Element
    val body : myBody,
)

@Xml(name = "header")
data class Header(
    @PropertyElement
    val resultCode : Int,
    @PropertyElement
    val resultMsg : String,
)

@Xml(name = "body")
data class myBody(
    @Element
    val items : myItems,
    @PropertyElement
    val numOfRows : Int,
    @PropertyElement
    val pageNo : Int,
    @PropertyElement
    val totalCount : Int,
)

@Xml(name = "items")
data class myItems(
    @Element(name="item")
    val item : MutableList<myItem>
)

@Xml
data class myItem(
    @PropertyElement
    val addr1 : String?,
    @PropertyElement
    val addr2 : String?,
    @PropertyElement
    val allar : Int?,
    @PropertyElement
    val animalCmgCl : String?,
    @PropertyElement
    val autoSiteCo : Int?,
    @PropertyElement
    val bizrno : String?,
    @PropertyElement
    val brazierCl : String?,
    @PropertyElement
    val caravAcmpnyAt : String?,
    @PropertyElement
    val caravSiteCo : String?,
    @PropertyElement
    val clturEventAt : String?,
    @PropertyElement
    val contentId : Int?,
    @PropertyElement
    val createdtime : String?,
    @PropertyElement
    val doNm : String?,
    @PropertyElement
    val eqpmnLendCl : String?,
    @PropertyElement
    val exprnProgrmAt : String?,
    @PropertyElement
    val extshrCo : Int?,
    @PropertyElement
    val facltDivNm : String?,
    @PropertyElement
    val facltNm : String?,
    @PropertyElement
    val featureNm : String?,
    @PropertyElement
    val fireSensorCo : Int?,
    @PropertyElement
    val firstImageUrl : String?,
    @PropertyElement
    val frprvtSandCo : Int?,
    @PropertyElement
    val frprvtWrppCo : Int?,
    @PropertyElement
    val glampSiteCo : Int?,
    @PropertyElement
    val gnrlSiteCo : Int?,
    @PropertyElement
    val homepage : String?,
    @PropertyElement
    val induty : String?,
    @PropertyElement
    val indvdlCaravSiteCo : Int?,
    @PropertyElement
    val insrncAt : String?,
    @PropertyElement
    val intro : String?,
    @PropertyElement
    val lctCl : String?,
    @PropertyElement
    val lineIntro : String?,
    @PropertyElement
    val manageNmpr : Int?,
    @PropertyElement
    val manageSttus : String?,
    @PropertyElement
    val mangeDivNm : String?,
    @PropertyElement
    val mapX : Double?,
    @PropertyElement
    val mapY : Double?,
    @PropertyElement
    val mgcDiv : String?,
    @PropertyElement
    val modifiedtime : String?,
    @PropertyElement
    val operDeCl : String?,
    @PropertyElement
    val operPdCl : String?,
    @PropertyElement
    val posblFcltyCl : String?,
    @PropertyElement
    val prmisnDe : String?,
    @PropertyElement
    val resveCl : String?,
    @PropertyElement
    val resveUrl : String?,
    @PropertyElement
    val sbrsCl : String?,
    @PropertyElement
    val sbrsEtc : String?,
    @PropertyElement
    val sigunguNm : String?,
    @PropertyElement
    val siteBottomCl1 : Int?,
    @PropertyElement
    val siteBottomCl2 : Int?,
    @PropertyElement
    val siteBottomCl3 : Int?,
    @PropertyElement
    val siteBottomCl4 : Int?,
    @PropertyElement
    val siteBottomCl5 : Int?,
    @PropertyElement
    val siteMg1Co : Int?,
    @PropertyElement
    val siteMg1Vrticl : Int?,
    @PropertyElement
    val siteMg1Width : Int?,
    @PropertyElement
    val siteMg2Co : Int?,
    @PropertyElement
    val siteMg2Vrticl : Int?,
    @PropertyElement
    val siteMg2Width : Int?,
    @PropertyElement
    val siteMg3Co : Int?,
    @PropertyElement
    val siteMg3Vrticl : Int?,
    @PropertyElement
    val siteMg3Width : Int?,
    @PropertyElement
    val sitedStnc : Int?,
    @PropertyElement
    val swrmCo : Int?,
    @PropertyElement
    val tel : String?,
    @PropertyElement
    val themaEnvrnCl : String?,
    @PropertyElement
    val toiletCo : Int?,
    @PropertyElement
    val trlerAcmpnyAt : String?,
    @PropertyElement
    val wtrplCo : Int?,
    @PropertyElement
    val zipcode : Long?,
) {
    constructor() : this(null,null,null,null,null,null,null,null,null,null,null,null,
        null,null,null,null,null,null,null,null,
        null,null,null,null,null,null,null,null,
        null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null
    ,null,null,null,null,null,null,null,null,null,null,null,null
    ,null,null,null,null,null,null,null,null,null,null,null,null, null, null)
}

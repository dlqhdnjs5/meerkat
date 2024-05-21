package com.project.meerkat.model.apartment

import javax.validation.constraints.NotEmpty
import javax.xml.bind.annotation.XmlAccessType
import javax.xml.bind.annotation.XmlAccessorType
import javax.xml.bind.annotation.XmlElement
import javax.xml.bind.annotation.XmlRootElement

@XmlRootElement(name = "response")
@XmlAccessorType(XmlAccessType.FIELD)
data class ApartmentTradeResponse(
    @XmlElement(name = "header") var header: TradeResponseHeader? = null,
    @XmlElement(name = "body") var body: TradeResponseBody? = null
)

@XmlAccessorType(XmlAccessType.FIELD)
data class TradeResponseHeader(
    @XmlElement(name = "resultCode") var resultCode: String? = null,
    @XmlElement(name = "resultMsg") var resultMsg: String? = null
)

@XmlAccessorType(XmlAccessType.FIELD)
data class TradeResponseBody(
    @XmlElement(name = "items") var items: TradeResponseBodyItem? = null,
    @XmlElement(name = "numOfRows") var numOfRows: String? = null,
    @XmlElement(name = "pageNo") var pageNo: String? = null,
    @XmlElement(name = "totalCount") var totalCount: String? = null
)

@XmlAccessorType(XmlAccessType.FIELD)
data class TradeResponseBodyItem(
    @XmlElement(name = "item") var item: List<TradeTransactionItem>? = null
)

@XmlRootElement(name="item")
@XmlAccessorType(XmlAccessType.FIELD)
data class TradeTransactionItem(
    @get:NotEmpty @field:XmlElement(name = "거래금액") var dealAmount: String? = null,
    @get:NotEmpty @field:XmlElement(name = "건축년도") var buildYear: String? = null,
    @get:NotEmpty @field:XmlElement(name = "년") var year: String? = null,
    @get:NotEmpty @field:XmlElement(name = "도로명") var roadName: String? = null,
    @get:NotEmpty @field:XmlElement(name = "도로명건물본번호코드") var roadNameBonbun: String? = null,
    @get:NotEmpty @field:XmlElement(name = "도로명건물부번호코드") var roadNameBubun: String? = null,
    @get:NotEmpty @field:XmlElement(name = "도로명시군구코드") var roadNameSigunguCode: String? = null,
    @get:NotEmpty @field:XmlElement(name = "도로명일련번호코드") var roadNameSeq: String? = null,
    @get:NotEmpty @field:XmlElement(name = "도로명지상지하코드") var roadNameBasementCode: String? = null,
    @get:NotEmpty @field:XmlElement(name = "도로명코드") var roadNameCode: String? = null,
    @get:NotEmpty @field:XmlElement(name = "법정동") var dong: String? = null,
    @get:NotEmpty @field:XmlElement(name = "법정동본번코드") var bonbun: String? = null,
    @get:NotEmpty @field:XmlElement(name = "법정동부번코드") var bubun: String? = null,
    @get:NotEmpty @field:XmlElement(name = "법정동시군구코드") var sigunguCode: String? = null,
    @get:NotEmpty @field:XmlElement(name = "법정동읍면동코드") var eubmyundongCode: String? = null,
    @get:NotEmpty @field:XmlElement(name = "법정동지번코드") var landCode: String? = null,
    @get:NotEmpty @field:XmlElement(name = "아파트") var apartmentName: String? = null,
    @get:NotEmpty @field:XmlElement(name = "월") var month: String? = null,
    @get:NotEmpty @field:XmlElement(name = "일") var day: String? = null,
    @get:NotEmpty @field:XmlElement(name = "전용면적") var exclusiveArea: String? = null,
    @get:NotEmpty @field:XmlElement(name = "지번") var jibun: String? = null,
    @get:NotEmpty @field:XmlElement(name = "지역코드") var regionalCode: String? = null,
    @get:NotEmpty @field:XmlElement(name = "층") var floor: String? = null,
    @get:NotEmpty @field:XmlElement(name = "해제여부") var cancelDealType: String? = null,
    @get:NotEmpty @field:XmlElement(name = "해제사유발생일") var cancelDealDay: String? = null,
    @get:NotEmpty @field:XmlElement(name = "거래유형") var reqGbn: String? = null,
    @get:NotEmpty @field:XmlElement(name = "중개사소재지") var rdealerLawdnm: String? = null,
    @get:NotEmpty @field:XmlElement(name = "등기일자") var registrationDate: String? = null,
    @get:NotEmpty @field:XmlElement(name = "매도자") var sellerGbn: String? = null,
    @get:NotEmpty @field:XmlElement(name = "매수자") var buyerGbn: String? = null,
    @get:NotEmpty @field:XmlElement(name = "동") var apartmentDong: String? = null
)
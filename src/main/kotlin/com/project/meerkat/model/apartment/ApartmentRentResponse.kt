package com.project.meerkat.model.apartment

import javax.validation.constraints.NotEmpty
import javax.xml.bind.annotation.XmlAccessType
import javax.xml.bind.annotation.XmlAccessorType
import javax.xml.bind.annotation.XmlElement
import javax.xml.bind.annotation.XmlRootElement

@XmlRootElement(name = "response")
@XmlAccessorType(XmlAccessType.FIELD)
data class ApartmentRentResponse(
    @XmlElement(name = "header") var header: RentResponseHeader? = null,
    @XmlElement(name = "body") var body: RentResponseBody? = null
)
@XmlAccessorType(XmlAccessType.FIELD)
data class RentResponseHeader(
    @XmlElement(name = "resultCode") var resultCode: String? = null,
    @XmlElement(name = "resultMsg") var resultMsg: String? = null
)
@XmlAccessorType(XmlAccessType.FIELD)
data class RentResponseBody(
    @XmlElement(name = "items") var items: RentResponseBodyItem? = null,
    @XmlElement(name = "numOfRows") var numOfRows: String? = null,
    @XmlElement(name = "pageNo") var pageNo: String? = null,
    @XmlElement(name = "totalCount") var totalCount: String? = null
)
@XmlAccessorType(XmlAccessType.FIELD)
data class RentResponseBodyItem(
    @XmlElement(name = "item") var item: List<RentTransactionItem>? = null
)

@XmlRootElement(name="item")
@XmlAccessorType(XmlAccessType.FIELD)
data class RentTransactionItem(
    @get:NotEmpty @field:XmlElement(name = "갱신요구권사용", required = false) var useRequestRenewalContractRight: String? = null,
    @get:NotEmpty @field:XmlElement(name = "건축년도") var buildYear: String? = null,
    @get:NotEmpty @field:XmlElement(name = "계약구분", required = false) var contractType: String? = null,
    @get:NotEmpty @field:XmlElement(name = "계약기간", required = false) var termOfContract: String? = null,
    @get:NotEmpty @field:XmlElement(name = "년") var dealYear: String? = null,
    @get:NotEmpty @field:XmlElement(name = "법정동") var dong: String? = null,
    @get:NotEmpty @field:XmlElement(name = "보증금액") var deposit: String? = null,
    @get:NotEmpty @field:XmlElement(name = "아파트") var apartmentName: String? = null,
    @get:NotEmpty @field:XmlElement(name = "월") var dealMonth: String? = null,
    @get:NotEmpty @field:XmlElement(name = "월세금액") var monthlyRent: String? = null,
    @get:NotEmpty @field:XmlElement(name = "일") var dealDay: String? = null,
    @get:NotEmpty @field:XmlElement(name = "전용면적") var areaForExclusiveUse: String? = null,
    @get:NotEmpty @field:XmlElement(name = "종전계약보증금", required = false) var previousDeposit: String? = null,
    @get:NotEmpty @field:XmlElement(name = "종전계약월세", required = false) var previousMonthlyRent: String? = null,
    @get:NotEmpty @field:XmlElement(name = "지번") var jibun: String? = null,
    @get:NotEmpty @field:XmlElement(name = "지역코드") var regionalCode: String? = null,
    @get:NotEmpty @field:XmlElement(name = "층") var floor: String? = null
)
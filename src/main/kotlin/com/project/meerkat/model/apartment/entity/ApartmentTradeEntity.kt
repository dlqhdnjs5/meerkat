package com.project.meerkat.model.apartment.entity

import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Table(name = "apartment_trade")
data class ApartmentTradeEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "apartment_trade_no")
    val apartmentTradeNo: Int = 0,

    @Column(name = "dealAmount")
    var dealAmount: String? = null,

    @Column(name = "buildYear")
    var buildYear: String? = null,

    @Column(name = "year")
    var year: String? = null,

    @Column(name = "roadName")
    var roadName: String? = null,

    @Column(name = "roadNameBonbun")
    var roadNameBonbun: String? = null,

    @Column(name = "roadNameBubun")
    var roadNameBubun: String? = null,

    @Column(name = "roadNameSigunguCode")
    var roadNameSigunguCode: String? = null,

    @Column(name = "roadNameSeq")
    var roadNameSeq: String? = null,

    @Column(name = "roadNameBasementCode")
    var roadNameBasementCode: String? = null,

    @Column(name = "roadNameCode")
    var roadNameCode: String? = null,

    @Column(name = "dong")
    var dong: String? = null,

    @Column(name = "bonbun")
    var bonbun: String? = null,

    @Column(name = "bubun")
    var bubun: String? = null,

    @Column(name = "sigunguCode")
    var sigunguCode: String? = null,

    @Column(name = "eubmyundongCode")
    var eubmyundongCode: String? = null,

    @Column(name = "landCode")
    var landCode: String? = null,

    @Column(name = "apartmentName")
    var apartmentName: String? = null,

    @Column(name = "month")
    var month: String? = null,

    @Column(name = "day")
    var day: String? = null,

    @Column(name = "exclusiveArea")
    var exclusiveArea: String? = null,

    @Column(name = "jibun")
    var jibun: String? = null,

    @Column(name = "regionalCode")
    var regionalCode: String? = null,

    @Column(name = "floor")
    var floor: String? = null,

    @Column(name = "cancelDealType")
    var cancelDealType: String? = null,

    @Column(name = "cancelDealDay")
    var cancelDealDay: LocalDateTime? = null,

    @Column(name = "reqGbn")
    var reqGbn: String? = null,

    @Column(name = "rdealerLawdnm")
    var rdealerLawdnm: String? = null,

    @Column(name = "registrationDate")
    var registrationDate: LocalDateTime? = null,

    @Column(name = "sellerGbn")
    var sellerGbn: String? = null,

    @Column(name = "buyerGbn")
    var buyerGbn: String? = null,

    @Column(name = "apartmentDong")
    var apartmentDong: String? = null,

    @Column(name = "mig_date")
    var migDate: LocalDateTime
)

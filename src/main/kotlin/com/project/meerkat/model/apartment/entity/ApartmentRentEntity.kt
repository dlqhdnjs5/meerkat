package com.project.meerkat.model.apartment.entity

import java.time.LocalDateTime
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.EntityListeners
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.PrePersist
import javax.persistence.PreUpdate
import javax.persistence.Table
@EntityListeners(ApartmentRentEntityListener::class)
@Entity
@Table(name = "apartment_rent")
data class ApartmentRentEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "apartment_rent_no")
    val apartmentRentNo: Int = 0,

    @Column(name = "useRequestRenewalContractRight", length = 20)
    var useRequestRenewalContractRight: String? = null,

    @Column(name = "buildYear", length = 4)
    var buildYear: String? = null,

    @Column(name = "contractType", length = 20)
    var contractType: String? = null,

    @Column(name = "termOfContract", length = 20)
    var termOfContract: String? = null,

    @Column(name = "dealYear", length = 4)
    var dealYear: String? = null,

    @Column(name = "dong", length = 20)
    var dong: String? = null,

    @Column(name = "deposit")
    var deposit: String? = null,

    @Column(name = "apartmentName", length = 100)
    var apartmentName: String? = null,

    @Column(name = "dealMonth", length = 2)
    var dealMonth: String? = null,

    @Column(name = "monthlyRent")
    var monthlyRent: String? = null,

    @Column(name = "dealDay", length = 6)
    var dealDay: String? = null,

    @Column(name = "areaForExclusiveUse")
    var areaForExclusiveUse: String? = null,

    @Column(name = "previousDeposit")
    var previousDeposit: String? = null,

    @Column(name = "previousMonthlyRent")
    var previousMonthlyRent: String? = null,

    @Column(name = "jibun")
    var jibun: String? = null,

    @Column(name = "regionalCode", length = 5)
    var regionalCode: String? = null,

    @Column(name = "floor", length = 4)
    var floor: String? = null,

    @Column(name = "mig_date")
    var migDate: LocalDateTime
)

class ApartmentRentEntityListener {
    @PrePersist
    @PreUpdate
    fun preprocess(entity: ApartmentRentEntity) {
        entity.useRequestRenewalContractRight = entity.useRequestRenewalContractRight.trimToNull()
        entity.buildYear = entity.buildYear.trimToNull()
        entity.contractType = entity.contractType.trimToNull()
        entity.termOfContract = entity.termOfContract.trimToNull()
        entity.dealYear = entity.dealYear.trimToNull()
        entity.dong = entity.dong.trimToNull()
        entity.deposit = entity.deposit.trimToNull()
        entity.apartmentName = entity.apartmentName.trimToNull()
        entity.dealMonth = entity.dealMonth.trimToNull()
        entity.monthlyRent = entity.monthlyRent.trimToNull()
        entity.dealDay = entity.dealDay.trimToNull()
        entity.areaForExclusiveUse = entity.areaForExclusiveUse.trimToNull()
        entity.previousDeposit = entity.previousDeposit.trimToNull()
        entity.previousMonthlyRent = entity.previousMonthlyRent.trimToNull()
        entity.jibun = entity.jibun.trimToNull()
        entity.regionalCode = entity.regionalCode.trimToNull()
        entity.floor = entity.floor.trimToNull()
    }
}

fun String?.trimToNull() = this?.trim().takeIf { it?.isNotEmpty() == true }

package com.project.meerkat.service.apartment

import com.project.meerkat.common.GlobalPropertySource
import com.project.meerkat.model.apartment.ApartmentRentResponse
import com.project.meerkat.model.apartment.ApartmentTradeResponse
import com.project.meerkat.model.apartment.RentTransactionItem
import com.project.meerkat.model.apartment.TradeTransactionItem
import com.project.meerkat.model.apartment.entity.ApartmentRentEntity
import com.project.meerkat.model.apartment.entity.ApartmentTradeEntity
import com.project.meerkat.repository.apartment.ApartmentJpaRepository
import com.project.meerkat.repository.apartment.ApartmentRepository
import org.modelmapper.ModelMapper
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.springframework.util.CollectionUtils
import org.springframework.web.client.RestTemplate
import org.springframework.web.util.UriComponentsBuilder
import javax.transaction.Transactional

@Service
class ApartmentService(
    val restTemplate: RestTemplate,
    val modelMapper: ModelMapper,
    val apartmentJpaRepository: ApartmentJpaRepository,
    val apartmentRepository: ApartmentRepository,
    val globalPropertySource: GlobalPropertySource
) {
    companion object {
        val logger: Logger = LoggerFactory.getLogger(ApartmentService::class.java)
        const val APARTMENT_RENT_API_URL = "http://openapi.molit.go.kr:8081/OpenAPI_ToolInstallPackage/service/rest/RTMSOBJSvc/getRTMSDataSvcAptRent"
        const val APARTMENT_TRADE_API_URL = "http://openapi.molit.go.kr/OpenAPI_ToolInstallPackage/service/rest/RTMSOBJSvc/getRTMSDataSvcAptTradeDev"
    }
    fun getApartmentRents(lawdCd: List<String>, dealYmd: String): List<ApartmentRentEntity> {
        val items = mutableListOf<RentTransactionItem>()

        lawdCd.forEach {
            val url = UriComponentsBuilder
                .fromHttpUrl(APARTMENT_RENT_API_URL)
                .queryParam("LAWD_CD", it)
                .queryParam("DEAL_YMD", dealYmd)
                .queryParam("serviceKey", globalPropertySource.publicApiServiceKey)
                .build().toUriString()

            val response = restTemplate.getForObject(url, ApartmentRentResponse::class.java)
            val item = response?.body?.items?.item

            if (!CollectionUtils.isEmpty(item)) {
                items.addAll(item!!)
            }
        }

        return items.map { modelMapper.map(it, ApartmentRentEntity::class.java) }
    }

    fun getApartmentTrades(lawdCd: List<String>, dealYmd: String): List<ApartmentTradeEntity> {
        val items = mutableListOf<TradeTransactionItem>()

        lawdCd.forEach {
            val url = UriComponentsBuilder
                .fromHttpUrl(APARTMENT_TRADE_API_URL)
                .queryParam("LAWD_CD", it)
                .queryParam("pageNo", "1")
                .queryParam("numOfRows", "9999")
                .queryParam("DEAL_YMD", dealYmd)
                .queryParam("serviceKey", globalPropertySource.publicApiServiceKey)
                .build().toUriString()

            val response = restTemplate.getForObject(url, ApartmentTradeResponse::class.java)
            val item = response?.body?.items?.item

            if (!CollectionUtils.isEmpty(item)) {
                items.addAll(item!!)
            }
        }

        return items.map { modelMapper.map(it, ApartmentTradeEntity::class.java) }
    }

    @Transactional // TODO bulk insert로 변경할것.
    fun addApartmentRent(apartmentRentEntity: ApartmentRentEntity) {
        apartmentJpaRepository.insertApartmentRent(apartmentRentEntity)
    }

    @Transactional
    fun addApartmentTrades(apartmentRentEntities: List<ApartmentTradeEntity>) {
        apartmentRepository.insertApartmentTrades(apartmentRentEntities)
    }
}
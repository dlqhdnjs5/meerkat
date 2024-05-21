package com.project.meerkat.repository.apartment

import com.project.meerkat.model.apartment.entity.ApartmentTradeEntity
import org.apache.ibatis.annotations.Mapper
import org.apache.ibatis.annotations.Param
import org.springframework.stereotype.Repository

@Repository
@Mapper
interface ApartmentRepository {
    fun insertApartmentTrades(@Param("apartmentTradeEntities") apartmentTradeEntities: List<ApartmentTradeEntity>)
}
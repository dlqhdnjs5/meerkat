package com.project.meerkat.repository.apartment

import com.project.meerkat.model.apartment.entity.ApartmentRentEntity
import org.springframework.stereotype.Repository
import javax.persistence.EntityManager
import javax.persistence.PersistenceContext

@Repository
class ApartmentJpaRepository(
    @PersistenceContext private val entityManager: EntityManager
) {
    fun insertApartmentRent(apartmentRentEntity: ApartmentRentEntity) {
        entityManager.persist(apartmentRentEntity)
    }
}
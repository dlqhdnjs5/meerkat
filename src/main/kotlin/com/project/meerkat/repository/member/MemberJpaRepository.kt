package com.project.meerkat.repository.member

import com.project.meerkat.model.member.entity.MemberEntity
import org.springframework.stereotype.Repository
import java.time.LocalDateTime
import javax.persistence.EntityManager
import javax.persistence.PersistenceContext
import javax.persistence.TypedQuery


@Repository
class MemberJpaRepository(
    @PersistenceContext private val entityManager: EntityManager
) {

    fun findMemberByEmail(email: String): MemberEntity? {
        val jpql = "SELECT m FROM MemberEntity m WHERE m.email = :email"
        val query: TypedQuery<MemberEntity> = entityManager.createQuery(jpql, MemberEntity::class.java)
        query.setParameter("email", email)
        return query.resultList.firstOrNull()
    }
    fun insertMember(memberEntity: MemberEntity) {
        entityManager.persist(memberEntity)
    }

    fun updateLastLoginTime(memberEntity: MemberEntity) {
        memberEntity.lastLoginTime = LocalDateTime.now()
        entityManager.merge(memberEntity)
    }

}
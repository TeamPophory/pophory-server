package com.pophory.pophoryapi.member.application

import com.pophory.pophorydomain.member.Member
import com.pophory.pophorydomain.member.infrastructure.MemberJpaRepository
import jakarta.persistence.EntityNotFoundException
import org.springframework.stereotype.Component

@Component
class MemberFinder(
    private val memberJpaRepository: MemberJpaRepository
) {

    fun findById(id: Long) : Member {
        return memberJpaRepository.findById(id)
            .orElseThrow { EntityNotFoundException("Member not found") }
    }

    fun findByIdForUpdate(id: Long) : Member {
        return memberJpaRepository.findByIdForUpdate(id)
            .orElseThrow { EntityNotFoundException("Member not found") }
    }

}
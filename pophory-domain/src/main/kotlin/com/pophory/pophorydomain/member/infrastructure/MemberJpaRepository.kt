package com.pophory.pophorydomain.member.infrastructure

import com.pophory.pophorydomain.member.Member
import com.pophory.pophorydomain.member.SocialType
import jakarta.persistence.LockModeType
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Lock
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import java.util.*

interface MemberJpaRepository : JpaRepository<Member, Long> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select m from Member m where m.id = :id")
    fun findByIdForUpdate(@Param("id") id: Long) : Optional<Member?>?
    fun existsMemberByNickname(nickname: String?): Boolean
    fun existsMemberBySocialIdAndSocialType(socialId: String?, socialType: SocialType?): Boolean

    fun getMemberBySocialIdAndSocialType(socialId: String?, socialType: SocialType?): Optional<Member?>?

    fun findByNickname(nickname: String?): Optional<Member?>?

    fun findBySocialId(socialId: String?): Optional<Member?>?
}
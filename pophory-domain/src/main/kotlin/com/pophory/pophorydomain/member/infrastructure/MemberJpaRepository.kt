package com.pophory.pophorydomain.member.infrastructure

import com.pophory.pophorydomain.member.Member
import com.pophory.pophorydomain.member.SocialType
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface MemberJpaRepository : JpaRepository<Member, Long> {
    fun existsMemberByNickname(nickname: String?): Boolean
    fun existsMemberBySocialIdAndSocialType(socialId: String?, socialType: SocialType?): Boolean

    fun getMemberBySocialIdAndSocialType(socialId: String?, socialType: SocialType?): Optional<Member?>?

    fun findByNickname(nickname: String?): Optional<Member?>?

    fun findBySocialId(socialId: String?): Optional<Member?>?
}
package com.pophory.pophoryapi.push

import com.pophory.pophorydomain.member.Member
import com.pophory.pophorydomain.member.infrastructure.MemberJpaRepository
import jakarta.persistence.EntityNotFoundException
import org.springframework.http.MediaType
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.client.RestClient


@Service
class PushService(
    private val memberJpaRepository: MemberJpaRepository
) {

    @Transactional(readOnly = true)
    fun sendPush(id: Long) {
        val member: Member =  memberJpaRepository.findById(id)
            .orElseThrow { throw EntityNotFoundException("존재하지 않는 회원입니다.") }
        val fcmToken = member.fcmToken

        val restClient = RestClient.create()
        restClient.post()
            .uri("")
            .contentType(MediaType.APPLICATION_JSON)
            .retrieve()

    }
}
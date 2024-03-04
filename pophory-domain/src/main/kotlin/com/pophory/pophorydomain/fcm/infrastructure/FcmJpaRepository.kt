package com.pophory.pophorydomain.fcm.infrastructure

import com.pophory.pophorydomain.fcm.FcmEntity
import org.springframework.data.jpa.repository.JpaRepository

interface FcmJpaRepository : JpaRepository<FcmEntity, Long> {
}
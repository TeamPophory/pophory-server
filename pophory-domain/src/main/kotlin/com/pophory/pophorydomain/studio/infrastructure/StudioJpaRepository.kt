package com.pophory.pophorydomain.studio.infrastructure

import com.pophory.pophorydomain.studio.Studio
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface StudioJpaRepository : JpaRepository<Studio, Long> {
    fun findByName(name: String?): Optional<Studio>
}
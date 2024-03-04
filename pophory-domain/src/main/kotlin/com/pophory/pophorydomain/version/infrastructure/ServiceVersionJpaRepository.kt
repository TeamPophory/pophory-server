package com.pophory.pophorydomain.version.infrastructure

import com.pophory.pophorydomain.version.OSType
import com.pophory.pophorydomain.version.ServiceVersion
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface ServiceVersionJpaRepository : JpaRepository<ServiceVersion, Long> {
    fun findByOsType(osType: OSType?): Optional<ServiceVersion>
}
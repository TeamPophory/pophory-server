package com.pophory.pophorydomain.advertisement.infrastructure

import com.pophory.pophorydomain.advertisement.Advertisement
import org.springframework.data.jpa.repository.JpaRepository

interface AdvertisementJpaRepository : JpaRepository<Advertisement, Int> {
}
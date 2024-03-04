package com.pophory.pophorydomain.albumtheme.infrastructure

import com.pophory.pophorydomain.albumtheme.AlbumDesign
import org.springframework.data.jpa.repository.JpaRepository

interface AlbumDesignJpaRepository : JpaRepository<AlbumDesign, Long> {
}
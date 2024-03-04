package com.pophory.pophorydomain.photo.infrastructure

import com.pophory.pophorydomain.album.Album
import com.pophory.pophorydomain.photo.Photo
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface PhotoJpaRepository : JpaRepository<Photo, Long> {
    fun findAllByAlbum(album: Album?): List<Photo?>?

    fun findByShareId(shareId: String?): Optional<Photo?>?
}
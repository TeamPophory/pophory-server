package com.pophory.pophorydomain.albumtheme.infrastructure;

import com.pophory.pophorydomain.albumtheme.AlbumCover;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlbumCoverJpaRepository extends JpaRepository<AlbumCover, Long> {
}

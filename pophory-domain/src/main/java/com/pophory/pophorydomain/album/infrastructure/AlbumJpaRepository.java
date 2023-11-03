package com.pophory.pophorydomain.album.infrastructure;


import com.pophory.pophorydomain.album.Album;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AlbumJpaRepository extends JpaRepository<Album, Long> {

    List<Album> findAllByMemberId(Long memberId);

    Optional<Album> findFirstByMemberId(Long memberId);
}
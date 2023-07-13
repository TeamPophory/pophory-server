package com.pophory.pophoryserver.domain.album;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AlbumJpaRepository extends JpaRepository<Album, Long> {

    List<Album> findAllByMemberId(Long memberId);
}
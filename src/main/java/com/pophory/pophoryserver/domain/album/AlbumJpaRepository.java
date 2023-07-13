package com.pophory.pophoryserver.domain.album;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AlbumJpaRepository extends JpaRepository<Album, Long> {

    List<Album> findAllByMemberId(Long memberId);
}
package com.pophory.pophoryserver.domain.album.repository;

import com.pophory.pophoryserver.domain.album.Album;

import java.util.List;

public interface AlbumCustomRepository {

    List<Album> findAlbumsByMemberId(Long memberId);
}

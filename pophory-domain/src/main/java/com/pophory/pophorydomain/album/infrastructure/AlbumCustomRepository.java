package com.pophory.pophorydomain.album.infrastructure;


import com.pophory.pophorydomain.album.Album;

import java.util.List;

public interface AlbumCustomRepository {

    List<Album> findAlbumsByMemberId(Long memberId);
}

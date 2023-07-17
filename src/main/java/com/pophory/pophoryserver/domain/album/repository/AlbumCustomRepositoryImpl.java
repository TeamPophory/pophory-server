package com.pophory.pophoryserver.domain.album.repository;

import com.pophory.pophoryserver.domain.album.Album;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.pophory.pophoryserver.domain.album.QAlbum.album;

@Repository
@RequiredArgsConstructor
public class AlbumCustomRepositoryImpl implements AlbumCustomRepository{

    private final JPAQueryFactory queryFactory;
    @Override
    public List<Album> findAlbumsByMemberId(Long memberId) {
        return queryFactory
                .select(album)
                .from(album)
                .where(album.member.id.eq(memberId))
                .fetch();
    }
}

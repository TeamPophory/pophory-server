package com.pophory.pophorydomain.album.infrastructure;

import com.pophory.pophorydomain.album.Album;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.pophory.pophorydomain.album.QAlbum.album;


@Repository
@RequiredArgsConstructor
public class AlbumCustomRepositoryImpl implements AlbumCustomRepository{

    private final JPAQueryFactory queryFactory;
    @Override
    public List<Album> findAlbumsByMemberId(Long memberId) {
        return queryFactory
                .selectFrom(album)
                .where(album.member.id.eq(memberId))
                .fetch();
    }
}

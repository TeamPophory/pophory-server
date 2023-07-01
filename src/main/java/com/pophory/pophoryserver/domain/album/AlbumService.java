package com.pophory.pophoryserver.domain.album;


import com.pophory.pophoryserver.domain.album.dto.response.AlbumGetResponseDto;
import com.pophory.pophoryserver.domain.album.dto.response.AlbumListGetResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AlbumService {

    private final AlbumJpaRepository albumJpaRepository;

    @Transactional(readOnly = true)
    public AlbumListGetResponseDto getAlbums(Long memberId) {
        return AlbumListGetResponseDto.of(
                albumJpaRepository.findAllByMemberId(memberId).stream()
                        .map(album -> AlbumGetResponseDto.of(album, album.getPhotoList().size()))
                        .collect(Collectors.toList())
        );
    }
}

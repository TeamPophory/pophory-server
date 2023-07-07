package com.pophory.pophoryserver.domain.album;


import com.pophory.pophoryserver.domain.album.dto.response.AlbumGetResponseDto;
import com.pophory.pophoryserver.domain.album.dto.response.AlbumListGetResponseDto;
import com.pophory.pophoryserver.domain.photo.Photo;
import com.pophory.pophoryserver.domain.photo.PhotoJpaRepository;
import com.pophory.pophoryserver.domain.photo.dto.response.PhotoGetResponseDto;
import com.pophory.pophoryserver.domain.photo.dto.response.PhotoListGetResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AlbumService {

    private final AlbumJpaRepository albumJpaRepository;
    private final PhotoJpaRepository photoJpaRepository;

    @Transactional(readOnly = true)
    public AlbumListGetResponseDto getAlbums(Long memberId) {
        return AlbumListGetResponseDto.of(
                albumJpaRepository.findAllByMemberId(memberId).stream()
                        .map(album -> AlbumGetResponseDto.of(album, album.getPhotoList().size()))
                        .collect(Collectors.toList())
        );
    }

    @Transactional(readOnly = true)
    public PhotoListGetResponseDto getPhotosByAlbum(Long albumId, Long memberId) {
        List<Photo> photos = new ArrayList<>();
        return PhotoListGetResponseDto.of(
                photoJpaRepository.findAllByAlbum(getAlbumById(albumId))
                        .stream()
                        .sorted(Comparator.comparing(Photo::getTakenAt)
                                .thenComparing(Photo::getCreatedAt))
                        .map(PhotoGetResponseDto::of)
                        .collect(Collectors.toList())
        );
    }

    private Album getAlbumById(Long albumId) {
        return albumJpaRepository.findById(albumId).orElseThrow(
                () -> new EntityNotFoundException("해당 앨범이 존재하지 않습니다. id=" + albumId)
        );
    }
}

package com.pophory.pophoryapi.album.application;


import com.pophory.pophoryapi.album.dto.request.AlbumDesignUpdateRequestDto;
import com.pophory.pophoryapi.album.dto.response.AlbumGetResponseDto;
import com.pophory.pophoryapi.album.dto.response.AlbumGetV2ResponseDto;
import com.pophory.pophoryapi.album.dto.response.AlbumListGetResponseDto;
import com.pophory.pophoryapi.album.dto.response.AlbumListGetV2ResponseDto;
import com.pophory.pophoryapi.photo.dto.response.PhotoGetResponseDto;
import com.pophory.pophoryapi.photo.dto.response.PhotoGetV2ResponseDto;
import com.pophory.pophoryapi.photo.dto.response.PhotoListGetResponseDto;
import com.pophory.pophoryapi.photo.dto.response.PhotoListGetV2ResponseDto;
import com.pophory.pophorydomain.album.Album;
import com.pophory.pophorydomain.album.infrastructure.AlbumRepository;
import com.pophory.pophorydomain.albumtheme.AlbumDesign;
import com.pophory.pophorydomain.albumtheme.infrastructure.AlbumDesignJpaRepository;
import com.pophory.pophorydomain.photo.Photo;
import com.pophory.pophorydomain.photo.infrastructure.PhotoJpaRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AlbumService {

    private final AlbumRepository albumRepository;
    private final PhotoJpaRepository photoJpaRepository;
    private final AlbumDesignJpaRepository albumDesignJpaRepository;

    @Transactional(readOnly = true)
    public AlbumListGetResponseDto getAlbums(Long memberId) {
        return AlbumListGetResponseDto.of(
                albumRepository.findAllByMemberId(memberId).stream()
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

    @Transactional(readOnly = true)
    public PhotoListGetV2ResponseDto getPhotosByAlbumV2(Long albumId, Long memberId) {
        List<Photo> photos = new ArrayList<>();
        return PhotoListGetV2ResponseDto.of (
                photoJpaRepository.findAllByAlbum(getAlbumById(albumId))
                        .stream()
                        .sorted(Comparator.comparing(Photo::getTakenAt)
                                .thenComparing(Photo::getCreatedAt).reversed())
                        .map(PhotoGetV2ResponseDto::of)
                        .collect(Collectors.toList())
        );
    }

    @Transactional(readOnly = true)
    public AlbumListGetV2ResponseDto getAlbumsV2(Long memberId) {
        return AlbumListGetV2ResponseDto.of(
                albumRepository.findAlbumsByMemberId(memberId).stream()
                        .map(album -> AlbumGetV2ResponseDto.of(album, album.getPhotoList().size()))
                        .collect(Collectors.toList())
        );
    }

    @Transactional(readOnly = true)
    public AlbumGetV2ResponseDto getAlbum(Long albumId) {
        Album album = getAlbumById(albumId);
        return AlbumGetV2ResponseDto.of(album, album.getPhotoList().size());
    }

    @Transactional
    public void updateAlbumDesign(AlbumDesignUpdateRequestDto request, Long albumId) {
        Album album = getAlbumById(albumId);
        album.updateAlbumDesign(getAlbumDesignById(request.getAlbumDesignId()));
    }

    private Album getAlbumById(Long albumId) {
        return albumRepository.findById(albumId).orElseThrow(
                () -> new EntityNotFoundException("해당 앨범이 존재하지 않습니다. id=" + albumId)
        );
    }

    private AlbumDesign getAlbumDesignById(Long albumDesignId) {
        return albumDesignJpaRepository.findById(albumDesignId).orElseThrow(
                () -> new EntityNotFoundException("해당 앨범 디자인이 존재하지 않습니다. id=")
        );
    }
}

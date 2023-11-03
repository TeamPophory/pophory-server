package com.pophory.pophoryapi.photo.application;


import com.pophory.pophoryapi.photo.dto.response.PhotoShareApproveResponseDto;
import com.pophory.pophoryapi.photo.dto.response.PhotoShareResponseDto;
import com.pophory.pophorycommon.exception.AlbumLimitExceedException;
import com.pophory.pophorycommon.exception.SelfApproveException;
import com.pophory.pophorydomain.album.Album;
import com.pophory.pophorydomain.album.infrastructure.AlbumJpaRepository;
import com.pophory.pophorydomain.photo.Photo;
import com.pophory.pophorydomain.photo.PhotoSizeVO;
import com.pophory.pophorydomain.photo.infrastructure.PhotoJpaRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;



@Service
@RequiredArgsConstructor
public class PhotoShareService {

    private final PhotoJpaRepository photoJpaRepository;
    private final AlbumJpaRepository albumJpaRepository;

    @Transactional(readOnly = true)
    public PhotoShareResponseDto getSharedPhoto(String shareId) {
        Photo photo = getPhotoByShareId(shareId);
        return PhotoShareResponseDto.of(photo, photo.getAlbum().getMember());
    }

    @Transactional
    public PhotoShareApproveResponseDto addPhotoFromShare(Long receiverId, Long sharedPhotoId) {
        Photo sharedPhoto = getPhotoById(sharedPhotoId);
        Album album = getAlbumByMemberId(receiverId);
        if (!album.checkPhotoLimit()) throw new AlbumLimitExceedException("앨범 한도를 초과했습니다.");
        if (sharedPhoto.getAlbum().getMember().getId().equals(receiverId)) {
            throw new SelfApproveException("자신의 사진은 수락할 수 없습니다.");
        }
        Photo photo = Photo.builder()
                .imageUrl(sharedPhoto.getImageUrl())
                .album(album)
                .studio(sharedPhoto.getStudio())
                .takenAt(sharedPhoto.getTakenAt())
                .photoSizeVO(PhotoSizeVO.of(sharedPhoto.getWidth(), sharedPhoto.getHeight()))
                .build();
        photoJpaRepository.save(photo);
        return PhotoShareApproveResponseDto.of(album);
    }

    private Album getAlbumByMemberId(Long memberId) {
        return albumJpaRepository.findFirstByMemberId(memberId).orElseThrow(
                () -> new EntityNotFoundException("해당 멤버의 앨범이 존재하지 않습니다."));
    }

    private Photo getPhotoByShareId(String shareId) {
        return photoJpaRepository.findByShareId(shareId).orElseThrow(
                () -> new IllegalArgumentException("해당 사진이 존재하지 않습니다."));
    }

    private Photo getPhotoById(Long id) {
        return photoJpaRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("공유할 사진이 존재하지 않습니다.")
        );
    }

}

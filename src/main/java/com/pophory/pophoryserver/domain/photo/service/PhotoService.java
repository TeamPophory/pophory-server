package com.pophory.pophoryserver.domain.photo.service;

import com.amazonaws.services.s3.model.ObjectMetadata;
import com.pophory.pophoryserver.domain.album.Album;
import com.pophory.pophoryserver.domain.album.repository.AlbumJpaRepository;
import com.pophory.pophoryserver.domain.member.MemberJpaRepository;
import com.pophory.pophoryserver.domain.photo.Photo;
import com.pophory.pophoryserver.domain.photo.PhotoJpaRepository;
import com.pophory.pophoryserver.domain.photo.dto.request.PhotoAddV1RequestDto;
import com.pophory.pophoryserver.domain.photo.dto.request.PhotoAddV2RequestDto;
import com.pophory.pophoryserver.domain.photo.dto.response.*;
import com.pophory.pophoryserver.domain.photo.vo.PhotoSizeVO;
import com.pophory.pophoryserver.domain.s3.UploadType;
import com.pophory.pophoryserver.domain.s3.dto.response.S3GetPresignedUrlResponseDto;
import com.pophory.pophoryserver.domain.studio.Studio;
import com.pophory.pophoryserver.domain.studio.StudioJpaRepository;
import com.pophory.pophoryserver.global.exception.AlbumLimitExceedException;
import com.pophory.pophoryserver.global.exception.BadRequestException;
import com.pophory.pophoryserver.global.exception.S3UploadException;
import com.pophory.pophoryserver.global.util.PhotoUtil;
import com.pophory.pophoryserver.domain.s3.S3Service;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PhotoService {

    private final PhotoJpaRepository photoJpaRepository;
    private final AlbumJpaRepository albumJpaRepository;
    private final StudioJpaRepository studioJpaRepository;
    private final S3Service s3Service;

    @Value("${cloud.aws.CLOUDFRONT}")
    private String CLOUD_FRONT_DOMAIN;

    private static final long MAX_FILE_SIZE = 3145728; // 3MB

    private static final String MEMBER_PREFIX = "member/";
    private final MemberJpaRepository memberJpaRepository;

    @Transactional
    public void addPhotoV1(MultipartFile file, PhotoAddV1RequestDto request, Long memberId) {
        validateFileSize(file);
        validateExt(file);
        Album album = getAlbumById(request.getAlbumId());
        if (!album.checkPhotoLimit()) throw new AlbumLimitExceedException("앨범 갯수 제한을 넘어갔습니다. albumId : " + album.getId());
        Studio studio = getStudioById(request.getStudioId());
        checkAlbumLimit(album);
        photoJpaRepository.save(Photo.builder()
                .imageUrl(upload(file, memberId))
                .album(album)
                .studio(studio)
                .takenAt(PhotoUtil.changeRequestToTakenAt(request.getTakenAt()))
                .photoSizeVO(getImageSize(file))
                .build());
    }

    @Transactional
    public PhotoAddResponseDto addPhotoV2(PhotoAddV2RequestDto request, Long memberId) {
        Album album = getAlbumById(request.getAlbumId());
        if (!album.checkPhotoLimit()) throw new AlbumLimitExceedException("앨범 갯수 제한을 넘어갔습니다. albumId : " + album.getId());
        Studio studio = getStudioById(request.getStudioId());
        Photo photo =  photoJpaRepository.save(Photo.builder()
                .imageUrl(CLOUD_FRONT_DOMAIN + "/" + UploadType.PHOTO.getName() + getPophoryIdByMemberId(memberId) + MEMBER_PREFIX + request.getFileName())
                .album(album)
                .studio(studio)
                .takenAt(PhotoUtil.changeRequestToTakenAt(request.getTakenAt()))
                .photoSizeVO(PhotoSizeVO.of(request.getWidth(), request.getHeight()))
                .build());
        return PhotoAddResponseDto.of(photo.getId());
    }

    @Transactional
    public void deletePhoto(Long photoId, Long memberId) {
        Photo photo = getPhotoById(photoId);
        if (!Objects.equals(photo.getAlbum().getMember().getId(),memberId)) {
            throw new BadRequestException("해당 사진의 주인이 아닙니다.");
        }
        photoJpaRepository.deleteById(photoId);
    }

    public PhotoAllListResponseDto getAllPhotosV2(Long memberId) {
        List<Photo> photos = new ArrayList<>();
        albumJpaRepository.findAllByMemberId(memberId).forEach(album -> photos.addAll(album.getPhotoList()));
        List<PhotoAllListV2ResponseDto> photoList = photos.stream()
                .sorted(Comparator.comparing(Photo::getTakenAt)
                        .thenComparing(Photo::getCreatedAt).reversed())
                .map(photo -> PhotoAllListV2ResponseDto.builder()
                        .photo(photo)
                        .build())
                .collect(Collectors.toList());
        return PhotoAllListResponseDto.of(photoList);
    }

    public S3GetPresignedUrlResponseDto getPresignedUrl(UploadType type, Long memberId) {
        String fileName = createJpgFileName();
        return S3GetPresignedUrlResponseDto.of(s3Service.getPresignedUrl(type.getName() + getPophoryIdByMemberId(memberId) + MEMBER_PREFIX + fileName), fileName);
    }
    private void checkAlbumLimit(Album album) {
        if (album.getPhotoList().size() >= album.getPhotoLimit()) throw new BadRequestException("앨범 갯수 제한을 넘어갔습니다.");
    }
    private PhotoSizeVO getImageSize(MultipartFile file) {
        try {
            BufferedImage image = ImageIO.read(file.getInputStream());
            return PhotoSizeVO.of(image.getWidth(), image.getHeight());
        } catch (IOException e) {
            throw new IllegalArgumentException("이미지를 읽어오는데 실패했습니다.");
        }
    }

    private String getPophoryIdByMemberId(Long memberId) {
        return memberJpaRepository.findById(memberId).orElseThrow(() -> new EntityNotFoundException("해당 멤버가 존재하지 않습니다. id=" + memberId)).getPophoryId();
    }

    private Photo getPhotoById(Long photoId) {
        return photoJpaRepository.findById(photoId).orElseThrow(() -> new EntityNotFoundException("해당 사진이 존재하지 않습니다. id=" + photoId));
    }

    private String createFileName() {
        return UUID.randomUUID().toString();
    }

    private String createJpgFileName() { return UUID.randomUUID() + ".jpg"; }

    private String upload(MultipartFile file, Long memberId) {
        try {
            String uploadPath = UploadType.PHOTO.getName() + memberId + MEMBER_PREFIX + createFileName() + "." + getExtension(file);
            s3Service.upload(file.getInputStream(), uploadPath, getObjectMetadata(file));
            return CLOUD_FRONT_DOMAIN+"/"+uploadPath;
        } catch (IOException e) {
            throw new S3UploadException("파일 업로드에 실패했습니다.");
        }
    }

    private void validateFileSize(MultipartFile file) {
        if (file.getSize() > MAX_FILE_SIZE) {
            throw new MaxUploadSizeExceededException(MAX_FILE_SIZE);
        }
    }


    private void validateExt(MultipartFile file) {
        List<String> ext = List.of("image/jpeg", "image/jpg");
        if (!ext.contains(file.getContentType())) {
            throw new IllegalArgumentException("지원하지 않는 확장명입니다.");
        }
    }

    private String getExtension(MultipartFile file) {
        String fileName = file.getOriginalFilename();
        return Objects.requireNonNull(fileName).substring(fileName.lastIndexOf(".") + 1);
    }

    private ObjectMetadata getObjectMetadata(MultipartFile file) {
        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentLength(file.getSize());
        objectMetadata.setContentType(file.getContentType());
        return objectMetadata;
    }

    private Album getAlbumById(Long albumId) {
        return albumJpaRepository.findById(albumId).orElseThrow(() -> new IllegalArgumentException("해당 앨범이 존재하지 않습니다. id=" + albumId));
    }

    private Studio getStudioById(Long studioId) {
        if (studioId == null) {
            return null;
        }
        return studioJpaRepository.findById(studioId).orElseThrow(
                () -> new IllegalArgumentException("해당 스튜디오가 존재하지 않습니다. id=" + studioId));
    }
}
package com.pophory.pophoryserver.domain.photo;

import com.amazonaws.services.s3.model.ObjectMetadata;
import com.pophory.pophoryserver.domain.album.Album;
import com.pophory.pophoryserver.domain.album.AlbumJpaRepository;
import com.pophory.pophoryserver.domain.photo.dto.PhotoAddRequestDto;
import com.pophory.pophoryserver.domain.studio.Studio;
import com.pophory.pophoryserver.domain.studio.StudioJpaRepository;
import com.pophory.pophoryserver.infrastructure.s3.S3Util;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PhotoService {

    private final PhotoJpaRepository photoJpaRepository;
    private final AlbumJpaRepository albumJpaRepository;
    private final StudioJpaRepository studioJpaRepository;
    private final S3Util s3Util;

    private static final long MAX_FILE_SIZE = 3145728; // 3MB

    @Transactional
    public void addPhoto(MultipartFile file, PhotoAddRequestDto request, Long memberId) {
        validateFileSize(file);
        validateExt(file);
        Album album = getAlbumById(request.getAlbumId());
        Studio studio = getStudioById(request.getStudioId());
        photoJpaRepository.save(Photo.builder()
                .imageUrl(upload(file, memberId))
                .album(album)
                .studio(studio)
                .build());
    }
    private String upload(MultipartFile file, Long memberId) {
        try {
            String uploadPath = "images/" + memberId + "member/" + UUID.randomUUID().toString() + "." + getExtension(file);
            return s3Util.upload(file.getInputStream(), uploadPath, getObjectMetadata(file));
        } catch (IOException e) {
            throw new IllegalStateException("파일 업로드에 실패했습니다.");
        }
    }

    private void validateFileSize(MultipartFile file) {
        if (file.getSize() > MAX_FILE_SIZE) {
            throw new IllegalArgumentException("파일의 크기가 너무 큽니다." + file.getSize());
        }
    }


    private void validateExt(MultipartFile file) {
        System.out.println(file.getContentType());
        List<String> exts = List.of("image/jpeg", "image/jpg");
        if (!exts.contains(file.getContentType())) {
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
        return studioJpaRepository.findById(studioId).orElseThrow(
                () -> new IllegalArgumentException("해당 스튜디오가 존재하지 않습니다. id=" + studioId));
    }
}

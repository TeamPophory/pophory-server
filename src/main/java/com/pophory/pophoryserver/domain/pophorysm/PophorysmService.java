package com.pophory.pophoryserver.domain.pophorysm;

import com.pophory.pophoryserver.domain.member.Member;
import com.pophory.pophoryserver.domain.member.MemberJpaRepository;
import com.pophory.pophoryserver.domain.photo.Photo;
import com.pophory.pophoryserver.domain.photo.PhotoJpaRepository;
import com.pophory.pophoryserver.domain.photo.vo.PhotoSizeVO;
import com.pophory.pophoryserver.domain.pophorysm.dto.request.PophorysmShareRequestDto;
import com.pophory.pophoryserver.domain.s3.S3Service;
import com.pophory.pophoryserver.domain.s3.UploadType;
import com.pophory.pophoryserver.domain.version.dto.response.PophorysmGetPresignedUrlResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PophorysmService {

    private final MemberJpaRepository memberJpaRepository;
    private final PhotoJpaRepository photoJpaRepository;
    private final S3Service s3Service;

    @Value("${pophorysm.id}")
    private Long pophorysmId;

    @Transactional(readOnly = true)
    public PophorysmGetPresignedUrlResponseDto getSharePresignedUrl(UploadType type, String nickname, Long memberId) {
        validatePophoryId(memberId);
        String pophoryId = getPophoryIdByMemberNickname(nickname);
        String filename = UUID.randomUUID() + ".jpg";
        String key = type.getName() + "member" + pophoryId + filename;
        return PophorysmGetPresignedUrlResponseDto.of(s3Service.getPresignedUrl(key),filename, pophoryId);
    }

    @Transactional
    public void sharePhoto(PophorysmShareRequestDto request, Long memberId) {
        validatePophoryId(memberId);
        Photo sharedPhoto = getPhotoById(request.getPhotoId());
        Member member = getMemberByNickname(request.getNickname());
        if (!member.getAlbumList().get(0).checkPhotoLimit()) {
            throw new IllegalArgumentException("앨범의 사진 개수가 초과되었습니다.");
        }
        Photo.builder()
                .album(member.getAlbumList().get(0))
                .studio(sharedPhoto.getStudio())
                .takenAt(sharedPhoto.getTakenAt())
                .imageUrl(sharedPhoto.getImageUrl())
                .photoSizeVO(PhotoSizeVO.of(request.getWidth(), request.getHeight()))
                .build();
    }

    private Member getMemberByNickname(String nickname) {
        return memberJpaRepository.findByNickname(nickname).orElseThrow(
                () -> new IllegalArgumentException("존재하지 않는 닉네임입니다."));
    }

    private Photo getPhotoById(Long id) {
        return photoJpaRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("존재하지 않는 사진입니다."));
    }

    private String getPophoryIdByMemberNickname(String nickname) {
        return memberJpaRepository.findByNickname(nickname).orElseThrow(
                () -> new IllegalArgumentException("존재하지 않는 닉네임입니다.")
        ).getPophoryId();
    }


    private void validatePophoryId(Long memberId) {
        if (!pophorysmId.equals(memberId)) {
            throw new IllegalArgumentException("포포리즘의 사진을 공유할 수 없습니다.");
        }
    }
}

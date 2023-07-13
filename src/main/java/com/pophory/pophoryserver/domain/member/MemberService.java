package com.pophory.pophoryserver.domain.member;

import com.pophory.pophoryserver.domain.album.Album;
import com.pophory.pophoryserver.domain.album.AlbumJpaRepository;
import com.pophory.pophoryserver.domain.albumtheme.AlbumCover;
import com.pophory.pophoryserver.domain.albumtheme.AlbumCoverJpaRepository;
import com.pophory.pophoryserver.domain.member.dto.request.MemberCreateRequestDto;
import com.pophory.pophoryserver.domain.member.dto.response.MemberGetResponseDto;
import com.pophory.pophoryserver.domain.member.dto.response.MemberMyPageGetResponseDto;
import com.pophory.pophoryserver.domain.member.dto.response.MemberNicknameDuplicateResponseDto;
import com.pophory.pophoryserver.domain.photo.Photo;
import com.pophory.pophoryserver.domain.photo.dto.response.PhotoGetResponseDto;
import com.pophory.pophoryserver.global.util.MemberUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberJpaRepository memberJpaRepository;
    private final AlbumJpaRepository albumJpaRepository;
    private final AlbumCoverJpaRepository albumCoverJpaRepository;

    private static final int INITIAL_PHOTO_LIMIT = 15;

    @Transactional
    public void update(MemberCreateRequestDto request, Long memberId) {
        checkNicknameDuplicate(request.getNickname());
        updateMemberInfo(request, memberId);
    }

    @Transactional(readOnly = true)
    public MemberGetResponseDto getMember(Long id) {
        return MemberGetResponseDto.of(findMemberById(id));
    }

    @Transactional(readOnly = true)
    public MemberMyPageGetResponseDto getMypageMember(Long memberId) {
        return MemberMyPageGetResponseDto.of(
                findMemberById(memberId), getPhotoCount(memberId), getPhotos(memberId)
        );
    }

    @Transactional(readOnly = true)
    public MemberNicknameDuplicateResponseDto checkDuplicateMemberNickname(String nickname) {
        return MemberNicknameDuplicateResponseDto.of(memberJpaRepository.existsMemberByNickname(nickname));
    }


    private void updateMemberInfo(MemberCreateRequestDto request, Long memberId) {
        Member member = findMemberById(memberId);
        member.updateRealName(request.getRealName());
        member.updateNickname(request.getNickname());
        member.generatePophoryId(MemberUtil.generateRandomString(6));
        addAlbum(member, request.getAlbumCover());
    }

    private void checkNicknameDuplicate(String nickName) {
        if (memberJpaRepository.existsMemberByNickname(nickName)) {
            throw new EntityExistsException("이미 존재하는 닉네임입니다. nickname: " + nickName);
        }
    }

    private Member findMemberById(Long id) {
        return memberJpaRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Member가 존재하지 않습니다. id: " + id)
        );
    }

    private void addAlbum(Member member, int cover) {
        Album album = new Album();
        AlbumCover albumCover = AlbumCover.builder()
                .coverNumber(cover)
                .build();
        albumCoverJpaRepository.save(albumCover);
        album.setCover(albumCover);
        album.setMember(member);
        album.setPhotoLimit(INITIAL_PHOTO_LIMIT);
        albumJpaRepository.save(album);
    }

    private int getPhotoCount(Long memberId) {
        return getAllAlbumByMemberId(memberId).stream().map(
                album -> album.getPhotoList().size())
                .mapToInt(Integer::intValue)
                .sum();
    }

    private List<PhotoGetResponseDto> getPhotos(Long memberId) {
        List<Photo> photos = new ArrayList<>();
        getAllAlbumByMemberId(memberId).forEach(album -> photos.addAll(album.getPhotoList()));
         return photos.stream().sorted(Comparator.comparing(Photo::getTakenAt)
                .thenComparing(Photo::getCreatedAt).reversed())
                 .map(PhotoGetResponseDto::of)
                 .collect(Collectors.toList());
    }

    private List<Album> getAllAlbumByMemberId(Long memberId) {
        return albumJpaRepository.findAllByMemberId(memberId);
    }
}

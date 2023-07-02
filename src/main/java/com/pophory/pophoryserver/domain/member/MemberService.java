package com.pophory.pophoryserver.domain.member;

import com.pophory.pophoryserver.domain.album.Album;
import com.pophory.pophoryserver.domain.album.AlbumJpaRepository;
import com.pophory.pophoryserver.domain.member.dto.request.MemberCreateRequestDto;
import com.pophory.pophoryserver.domain.member.dto.response.MemberGetResponseDto;
import com.pophory.pophoryserver.domain.member.dto.response.MemberMyPageGetResponseDto;
import com.pophory.pophoryserver.domain.photo.PhotoJpaRepository;
import com.pophory.pophoryserver.domain.photo.dto.response.PhotoGetResponseDto;
import com.pophory.pophoryserver.domain.photo.dto.response.PhotoListGetResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberJpaRepository memberJpaRepository;
    private final AlbumJpaRepository albumJpaRepository;

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

    private void updateMemberInfo(MemberCreateRequestDto request, Long memberId) {
        Member member = findMemberById(memberId);
        member.updateRealName(request.getRealName());
        member.updateNickname(request.getNickname());
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

    private void addAlbum(Member member, int albumCover) {
        Album album = new Album();
        album.setCover(albumCover);
        member.getAlbumList().add(album);
    }

    private int getPhotoCount(Long memberId) {
        return getAllAlbumByMemberId(memberId).stream().map(
                album -> album.getPhotoList().size())
                .mapToInt(Integer::intValue)
                .sum();
    }

    private PhotoListGetResponseDto getPhotos(Long memberId) {
        List<PhotoGetResponseDto> photoList = getAllAlbumByMemberId(memberId)
                .stream()
                .flatMap(
                        album -> album.getPhotoList().stream()
                                .map(PhotoGetResponseDto::of))
                .collect(Collectors.toList());
        return PhotoListGetResponseDto.of(photoList);
    }

    private List<Album> getAllAlbumByMemberId(Long memberId) {
        return albumJpaRepository.findAllByMemberId(memberId);
    }
}

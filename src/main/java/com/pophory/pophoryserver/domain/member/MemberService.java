package com.pophory.pophoryserver.domain.member;

import com.pophory.pophoryserver.domain.album.Album;
import com.pophory.pophoryserver.domain.album.repository.AlbumJpaRepository;
import com.pophory.pophoryserver.domain.albumtheme.AlbumDesign;
import com.pophory.pophoryserver.domain.albumtheme.AlbumDesignJpaRepository;
import com.pophory.pophoryserver.domain.fcm.FcmEntity;
import com.pophory.pophoryserver.domain.fcm.FcmJpaRepository;
import com.pophory.pophoryserver.domain.fcm.FcmOS;
import com.pophory.pophoryserver.domain.member.dto.request.MemberCreateRequestDto;
import com.pophory.pophoryserver.domain.member.dto.request.MemberCreateV2RequestDto;
import com.pophory.pophoryserver.domain.member.dto.response.*;
import com.pophory.pophoryserver.domain.photo.Photo;
import com.pophory.pophoryserver.domain.photo.dto.response.PhotoGetResponseDto;
import com.pophory.pophoryserver.domain.slack.SlackService;
import com.pophory.pophoryserver.global.util.RandomUtil;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MemberService {

    @Value("${slack.channel.signin}")
    private String SLACK_CHANNEL_SIGNIN;

    private final MemberJpaRepository memberJpaRepository;
    private final AlbumJpaRepository albumJpaRepository;
    private final AlbumDesignJpaRepository albumDesignJpaRepository;
    private final FcmJpaRepository fcmJpaRepository;

    private final SlackService slackService;

    private static final int INITIAL_PHOTO_LIMIT = 15;
    private static final String SLACK_MESSAGE = " \uD83C\uDF89 %s 님이 포포리의 회원가입을 완료했습니다. \uD83C\uDF89";

    @Transactional
    public void update(MemberCreateRequestDto request, Long memberId) {
        checkNicknameDuplicate(request.getNickname());
        slackService.sendMessage(SLACK_CHANNEL_SIGNIN, String.format(SLACK_MESSAGE, request.getNickname()));
        updateMemberInfo(request, memberId);
    }

    @Transactional
    public MemberCreateResponseDto updateV2(MemberCreateV2RequestDto request, Long memberId) {
        checkNicknameDuplicate(request.getNickname());
            slackService.sendMessage(SLACK_CHANNEL_SIGNIN, String.format(SLACK_MESSAGE, request.getNickname()));
        return MemberCreateResponseDto.of(updateMemberInfoV2(request, memberId));
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

    @Transactional(readOnly = true)
    public MemberMyPageGetV2ResponseDto getMypageMemberV2(Long memberId) {
        return MemberMyPageGetV2ResponseDto.of(
                findMemberById(memberId), getPhotoCount(memberId)
        );
    }

    @Transactional
    public void logout(Long memberId) {
        findMemberById(memberId).updateRefreshToken(null);
    }

    private void updateMemberInfo(MemberCreateRequestDto request, Long memberId) {
        Member member = findMemberById(memberId);
        member.updateRealName(request.getRealName());
        member.updateNickname(request.getNickname());
        member.generatePophoryId(RandomUtil.generateRandomString(8));
        addAlbum(member, request.getAlbumCover());
    }

    private Long updateMemberInfoV2(MemberCreateV2RequestDto request, Long memberId) {
        Member member = findMemberById(memberId);
        member.updateRealName(request.getRealName());
        member.updateNickname(request.getNickname());
        member.generatePophoryId(RandomUtil.generateRandomString(8));
        Long albumId = addAlbumV2(member, request.getAlbumCover());
        addFcmInfo(request.getFcmToken(), request.getFcmOS(), member);
        return albumId;
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

    private void addAlbum(Member member, Long cover) {
        Album album = new Album();
        album.setAlbumDesign(getAlbumDesignById(cover));
        album.setMember(member);
        album.setPhotoLimit(INITIAL_PHOTO_LIMIT);
        albumJpaRepository.save(album);
    }

    private Long addAlbumV2(Member member, Long cover) {
        Album album = new Album();
        album.setAlbumDesign(getAlbumDesignById(cover));
        album.setMember(member);
        album.setPhotoLimit(INITIAL_PHOTO_LIMIT);
        albumJpaRepository.save(album);
        return album.getId();
    }

    private void addFcmInfo(String fcmToken, FcmOS fcmOS, Member member) {
        FcmEntity fcmEntity = FcmEntity.builder()
                .fcmToken(fcmToken)
                .fcmOS(fcmOS)
                .member(member)
                .build();
        fcmJpaRepository.save(fcmEntity);
    }

    private int getPhotoCount(Long memberId) {
        return getAllAlbumByMemberId(memberId).stream().map(
                album -> album.getPhotoList().size())
                .mapToInt(Integer::intValue)
                .sum();
    }

    private AlbumDesign getAlbumDesignById(Long albumDesignId) {
        return albumDesignJpaRepository.findById(albumDesignId).orElseThrow(
                () -> new EntityNotFoundException("해당 앨범 디자인이 존재하지 않습니다. id:" + albumDesignId)
        );
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

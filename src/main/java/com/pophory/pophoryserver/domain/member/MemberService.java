package com.pophory.pophoryserver.domain.member;

import com.pophory.pophoryserver.domain.album.Album;
import com.pophory.pophoryserver.domain.member.dto.request.MemberCreateRequestDto;
import com.pophory.pophoryserver.domain.member.dto.response.MemberGetResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;

@Service
@RequiredArgsConstructor
public class MemberService {

    public final MemberJpaRepository memberJpaRepository;

    @Transactional
    public void update(MemberCreateRequestDto request, Long memberId) {
        checkNicknameDuplicate(request.getNickname());
        updateMemberInfo(request, memberId);
    }

    @Transactional(readOnly = true)
    public MemberGetResponseDto getMember(Long id) {
        return MemberGetResponseDto.of(findMemberById(id));
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
}

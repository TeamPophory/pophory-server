package com.pophory.pophoryserver.domain.member;

import com.pophory.pophoryserver.domain.album.Album;
import com.pophory.pophoryserver.domain.member.dto.request.MemberCreateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;

@Service
@RequiredArgsConstructor
public class MemberService {

    public final MemberJpaRepository memberJpaRepository;

    public void update(MemberCreateRequestDto request) {
        checkNicknameDuplicate(request.getNickname());
        updateMemberInfo(request);
    }

    private void updateMemberInfo(MemberCreateRequestDto request) {
        Member member = findMemberById(request.getId());
        member.updateRealName(request.getRealName());
        member.updateNickname(request.getNickname());
        addAlbum(member, request.getAlbumCover());
    }

    private void checkNicknameDuplicate(String nickName) {
        if (memberJpaRepository.existsMemberByNickname(nickName)) {
            throw  new EntityExistsException();
        }
    }

    private Member findMemberById(Long id) {
        return memberJpaRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException()
        );
    }

    private void addAlbum(Member member, int albumCover) {
        Album album = new Album();
        album.setCover(albumCover);
        member.getAlbumList().add(album);
    }
}

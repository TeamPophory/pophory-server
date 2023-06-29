package com.pophory.pophoryserver.domain.member;

import com.pophory.pophoryserver.domain.member.dto.request.MemberCreateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberService {

    public final MemberJpaRepository memberJpaRepository;

    @Transactional(readOnly = true)
    public void create(MemberCreateRequestDto request) {
        if (memberJpaRepository.existsMemberByNickname(request.getNickname())) {
            //아이디 중복 예외처리
        }


    }

}

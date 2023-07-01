package com.pophory.pophoryserver.domain.member.auth;

import com.pophory.pophoryserver.domain.member.MemberJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final MemberJpaRepository memberJpaRepository;

    public void signOut(Long memberId) {
        memberJpaRepository.deleteById(memberId);
    }
}

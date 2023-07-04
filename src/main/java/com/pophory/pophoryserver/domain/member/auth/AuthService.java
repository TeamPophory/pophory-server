package com.pophory.pophoryserver.domain.member.auth;

import com.pophory.pophoryserver.domain.member.MemberJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final MemberJpaRepository memberJpaRepository;

    @Transactional
    public void signOut(Long memberId) {
        memberJpaRepository.deleteById(memberId);
    }
}

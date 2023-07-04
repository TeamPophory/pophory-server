package com.pophory.pophoryserver.domain.member.auth;

import com.pophory.pophoryserver.domain.member.Member;
import com.pophory.pophoryserver.domain.member.MemberJpaRepository;
import com.pophory.pophoryserver.domain.member.auth.dto.TokenVO;
import com.pophory.pophoryserver.domain.member.auth.dto.response.TokenResponseDto;
import com.pophory.pophoryserver.global.config.jwt.JwtTokenProvider;
import com.pophory.pophoryserver.global.config.jwt.UserAuthentication;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final MemberJpaRepository memberJpaRepository;
    private final SocialService socialService;

    @Transactional
    public void signOut(Long memberId) {
        memberJpaRepository.deleteById(memberId);
    }

    @Transactional
    public TokenResponseDto reIssue(Long memberId) {
        Member member = getMemberById(memberId);
        TokenVO tokenVO = socialService.generateToken(new UserAuthentication(member.getId(), null, null));
        member.updateRefreshToken(tokenVO.getRefreshToken());
        return TokenResponseDto.of(tokenVO.getAccessToken(), tokenVO.getRefreshToken());
    }

    private Member getMemberById(Long memberId) {
        return memberJpaRepository.findById(memberId).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다. memberId: " + memberId));
    }

}

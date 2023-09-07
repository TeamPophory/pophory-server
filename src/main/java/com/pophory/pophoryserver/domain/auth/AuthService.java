package com.pophory.pophoryserver.domain.auth;

import com.pophory.pophoryserver.domain.auth.dto.TokenVO;
import com.pophory.pophoryserver.domain.auth.dto.response.TokenResponseDto;
import com.pophory.pophoryserver.domain.member.Member;
import com.pophory.pophoryserver.domain.member.MemberJpaRepository;
import com.pophory.pophoryserver.domain.member.MemberQueryRepository;
import com.pophory.pophoryserver.global.config.jwt.UserAuthentication;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
public class AuthService {

    private final MemberJpaRepository memberJpaRepository;
    private final MemberQueryRepository memberQueryRepository;
    private final SocialService socialService;

    @Transactional
    public void signOut(Long memberId) {
        memberJpaRepository.deleteById(memberId);
    }

    @Transactional
    public TokenResponseDto reIssue(Long memberId) {
        Member member = memberQueryRepository.findMemberById(memberId);
        TokenVO tokenVO = socialService.generateToken(new UserAuthentication(member.getId(), null, null));
        member.updateRefreshToken(tokenVO.getRefreshToken());
        return TokenResponseDto.of(tokenVO.getAccessToken(), tokenVO.getRefreshToken());
    }
}

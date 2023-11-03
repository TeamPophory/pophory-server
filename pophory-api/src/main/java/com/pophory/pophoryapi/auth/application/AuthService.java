package com.pophory.pophoryapi.auth.application;


import com.pophory.pophoryapi.auth.dto.TokenVO;
import com.pophory.pophoryapi.auth.dto.response.TokenResponseDto;
import com.pophory.pophorycommon.config.jwt.UserAuthentication;
import com.pophory.pophorydomain.member.Member;
import com.pophory.pophorydomain.member.infrastructure.MemberJpaRepository;
import com.pophory.pophorydomain.member.infrastructure.MemberQueryRepository;
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

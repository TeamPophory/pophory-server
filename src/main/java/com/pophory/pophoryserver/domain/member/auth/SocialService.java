package com.pophory.pophoryserver.domain.member.auth;

import com.pophory.pophoryserver.domain.member.Member;
import com.pophory.pophoryserver.domain.member.MemberJpaRepository;
import com.pophory.pophoryserver.domain.member.auth.dto.TokenVO;
import com.pophory.pophoryserver.domain.member.auth.dto.request.AuthRequestDto;
import com.pophory.pophoryserver.domain.member.auth.dto.response.AuthResponseDto;
import com.pophory.pophoryserver.global.config.jwt.JwtTokenProvider;
import com.pophory.pophoryserver.global.config.jwt.UserAuthentication;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;

@Service
@RequiredArgsConstructor
public abstract class SocialService {

    protected static final Long ACCESS_TOKEN_EXPIRATION_TIME = 60 * 60 * 1000 * 2 * 12 * 100L; // 2시간
    protected static final Long REFRESH_TOKEN_EXPIRATION_TIME = 60 * 60 * 1000 * 24 * 14L; // 2주
    private JwtTokenProvider jwtTokenProvider;
    private MemberJpaRepository memberJpaRepository;

    protected abstract String login(String socialAccessToken);

    public AuthResponseDto signIn(String socialAccessToken, AuthRequestDto authRequestDto) {
        SocialType socialType = authRequestDto.getSocialType();
        String socialId = login(socialAccessToken);

        boolean isRegisterd = memberJpaRepository.existsMemberBySocialIdAndSocialType(socialType, socialId);

        // 유저 정보가 존재하지 않을 때
        if (!isRegisterd) {
            Member member = Member.builder()
                    .socialId(socialId)
                    .socialType(socialType)
                    .build();
            memberJpaRepository.save(member);

            TokenVO tokenVO = generateToken(new UserAuthentication(member.getId(), null, null));
            member.updateRefreshToken(tokenVO.getRefreshToken());
            return AuthResponseDto.of(tokenVO.getAccessToken(), tokenVO.getAccessToken(), isRegisterd);
        }

        Member signedMember = memberJpaRepository.getMemberBySocialIdAndSocialType(socialType, socialId).orElseThrow(
                () -> new EntityNotFoundException()
        );
        TokenVO tokenVO = generateToken(new UserAuthentication(signedMember.getId(), null, null));

        return AuthResponseDto.of(tokenVO.getAccessToken(), tokenVO.getRefreshToken(), isRegisterd);
    }

    private TokenVO generateToken(Authentication authentication) {
        return TokenVO.of(
                jwtTokenProvider.generateToken(authentication, ACCESS_TOKEN_EXPIRATION_TIME),
                jwtTokenProvider.generateToken(authentication, REFRESH_TOKEN_EXPIRATION_TIME)
        );
    }
}

package com.pophory.pophoryapi.auth.application;

import com.pophory.pophoryapi.auth.dto.TokenVO;
import com.pophory.pophoryapi.auth.dto.request.AuthRequestDto;
import com.pophory.pophoryapi.auth.dto.response.AuthResponseDto;
import com.pophory.pophorycommon.config.jwt.JwtTokenProvider;
import com.pophory.pophorycommon.config.jwt.UserAuthentication;
import com.pophory.pophorydomain.member.Member;
import com.pophory.pophorydomain.member.SocialType;
import com.pophory.pophorydomain.member.infrastructure.MemberJpaRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

@Service
@RequiredArgsConstructor
public class SocialService {

    @Value("${jwt.access-token.expiration-time}")
    private long ACCESS_TOKEN_EXPIRATION_TIME;

    @Value("${jwt.refresh-token.expiration-time}")
    private long REFRESH_TOKEN_EXPIRATION_TIME; // 2주

    private final JwtTokenProvider jwtTokenProvider;
    private final MemberJpaRepository memberJpaRepository;
    private final KakaoAuthService kakaoAuthService;
    private final AppleAuthService appleAuthService;

    @Transactional
    public AuthResponseDto signIn(String socialAccessToken, AuthRequestDto authRequestDto) throws NoSuchAlgorithmException, InvalidKeySpecException {
        SocialType socialType = SocialType.valueOf(authRequestDto.getSocialType());
        String socialId = getSocialId(socialType, socialAccessToken);
        // 유저 정보가 존재하지 않을 때
        if (!memberJpaRepository.existsMemberBySocialIdAndSocialType(socialId, socialType)) {
            Member member = Member.builder()
                    .socialId(socialId)
                    .socialType(socialType)
                    .build();
            memberJpaRepository.save(member);
        }

        boolean isRegistered = false;

        Member signedMember = getMemberBySocialAndSocialId(socialType, socialId);
        if (signedMember.checkSignUpUpdated()) isRegistered = true;
        TokenVO tokenVO = generateToken(new UserAuthentication(signedMember.getId(), null, null));
        signedMember.updateRefreshToken(tokenVO.getRefreshToken());

        return AuthResponseDto.of(tokenVO, isRegistered);
    }

    private Member getMemberBySocialAndSocialId(SocialType socialType, String socialId) {
        return memberJpaRepository.getMemberBySocialIdAndSocialType(socialId, socialType).orElseThrow(
                () -> new EntityNotFoundException("member가 존재하지 않습니다.")
        );
    }

    protected TokenVO generateToken(Authentication authentication) {
        return TokenVO.of(
                jwtTokenProvider.generateToken(authentication, ACCESS_TOKEN_EXPIRATION_TIME),
                jwtTokenProvider.generateToken(authentication, REFRESH_TOKEN_EXPIRATION_TIME)
        );
    }

    private String getSocialId(SocialType socialType, String socialAccessToken) throws NoSuchAlgorithmException, InvalidKeySpecException {
        switch (socialType) {
            case KAKAO:
                return kakaoAuthService.login(socialAccessToken);
            case APPLE:
                return appleAuthService.login(socialAccessToken);
            default:
                throw new IllegalArgumentException("소셜 타입이 잘못되었습니다");
        }
    }
}

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
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

@Service
@RequiredArgsConstructor
public class SocialService {

    protected static final Long ACCESS_TOKEN_EXPIRATION_TIME = 60 * 60 * 1000 * 2L; // 2시간
    protected static final Long REFRESH_TOKEN_EXPIRATION_TIME = 60 * 60 * 1000 * 24 * 14L; // 2주

    private final JwtTokenProvider jwtTokenProvider;
    private final MemberJpaRepository memberJpaRepository;
    private final KakaoAuthService kakaoAuthService;
    private final AppleAuthService appleAuthService;

    @Transactional
    public AuthResponseDto signIn(String socialAccessToken, AuthRequestDto authRequestDto) throws NoSuchAlgorithmException, InvalidKeySpecException {
        SocialType socialType = SocialType.valueOf(authRequestDto.getSocialType());
        String socialId = getSocialId(socialType, socialAccessToken);
        boolean isRegistered = memberJpaRepository.existsMemberBySocialIdAndSocialType(socialId, socialType);

        // 유저 정보가 존재하지 않을 때
        if (!isRegistered) {
            Member member = Member.builder()
                    .socialId(socialId)
                    .socialType(socialType)
                    .build();
            memberJpaRepository.save(member);
        }

        Member signedMember = getMemberBySocialAndSocialId(socialType, socialId);
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

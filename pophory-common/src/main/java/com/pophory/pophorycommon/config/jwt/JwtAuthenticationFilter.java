package com.pophory.pophorycommon.config.jwt;

import io.sentry.Sentry;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;


import java.io.IOException;

import static com.pophory.pophorycommon.config.jwt.JwtValidationType.VALID_JWT;


// OncePerRequestFilter: 모든 요청에 대해 한 번씩 실행되도록 보장하는 필터
// 클라가 요청을 보낼 때  JWT 토큰을 함께 전송, 이 때 이 토큰을 추출하고 유효하다면 인증 및 권한을 부여함
// 인증 정보는 SecurityContextHolder에 저장
// SecurityContextHolder에: 인증된 사용자의 보안을 관리하는 컨텍스트 -> Authentication객체 제공(사용자의 인증정보, 권한정보 포함)
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtTokenProvider;

    // request: 클라이언트로부터 수신된 HTTP 요청 객체
    // response: 클라이언트에게 반환될 HTTP 반환 객체
    // filterChain: 다음 필터로 전달하기 위한 필터 체인 객체
    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain) throws ServletException, IOException {
        try {
            final String token = getJwtFromRequest(request);            // 헤더에서 JWT 토큰 추출
            if (jwtTokenProvider.validateToken(token) == VALID_JWT) {     // 토큰이 존재하고 유효한 토큰일 때만
                Long memberId = jwtTokenProvider.getUserFromJwt(token);
                UserAuthentication authentication = new UserAuthentication(memberId.toString(), null, null);       //사용자 객체 생성
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));  // request 정보로 사용자 객체 디테일 설정
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        } catch (Exception exception) {
            Sentry.captureException(exception);
        }
        filterChain.doFilter(request, response);            // 다음 필터로 요청 전달
    }

    // request 객체에서 JWT 토큰을 추출하는 메소드
    private String getJwtFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");        // 헤더에서 Authorization값 가져옴. 클라에서 보낸 인증토큰 포함될 수 있음
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {    // 가져온 토큰이 Bearer로 시작하고 비어있지 않은 경우
            return bearerToken.substring("Bearer ".length());                 // Bearer를 제외한 나머지 문장 반환 == 토큰
        }
        return null;
    }
}

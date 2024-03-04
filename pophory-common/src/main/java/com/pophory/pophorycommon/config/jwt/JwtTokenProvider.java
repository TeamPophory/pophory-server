package com.pophory.pophorycommon.config.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Date;

@Component                          //이 클래스를 스프링의 빈으로 등록하기 위해 사용하는 어노테이션(의존성 관리, 생명주기 관리 등을 위해)
@RequiredArgsConstructor            //초기화 되지않은 final 필드나, @NonNull 이 붙은 필드에 대해 생성자를 생성해 줌
@Slf4j
public class JwtTokenProvider {

    @Value("${jwt.secret}")
    private String JWT_SECRET;

    @PostConstruct  // 빈이 생성된 후 자동으로 호출되는 생성자
    protected void init() {
        //base64 라이브러리에서 encodeToString을 이용해서 byte[] 형식을 String 형식으로 변환
        JWT_SECRET = Base64.getEncoder().encodeToString(JWT_SECRET.getBytes(StandardCharsets.UTF_8));
    }

    // 토큰 생성 Authentication: Spring Security에서 인증 정보를 나타내기 위해 사용되는 인터페이스
    public String generateToken(Authentication authentication, Long tokenExpirationTime) {
        final Date now = new Date();

        // 클레임 생성
        final Claims claims = Jwts.claims()
                .setIssuedAt(now)               // 발급 시간
                .setExpiration(new Date(now.getTime() + tokenExpirationTime));      // 만료 시간

        //  getPrincipal: 인증 주체(principal)를 반환, 보통 인증된 사용자 의미. 주로 사용자의 식별자(ID)나 사용자 객체(User)를 포함]
        claims.put("memberId", authentication.getPrincipal());

        return Jwts.builder()
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE)       // 암호화설정 알고리즘 및 토큰 타입 사용, JWT의 메타데이터를 정의하며, 토큰의 유효성 검증이나 처리 시 필요한 정보를 제공
                .setClaims(claims)
                .signWith(getSigningKey())                          // 서명 추가 메서드.(토큰의 무결성과 신뢰성을 보장하기 위함)
                .compact();                                         // JWT를 문자열로 직렬화되어 변환
    }

    private SecretKey getSigningKey() {
        String encodedKey = Base64.getEncoder().encodeToString(JWT_SECRET.getBytes());       //SecretKey 통해 서명 생성
        return Keys.hmacShaKeyFor(encodedKey.getBytes());   //일반적으로 HMAC (Hash-based Message Authentication Code) 알고리즘 사용
    }

    public JwtValidationType validateToken(String token) {
        try {
            final Claims claims = getBody(token);
            return JwtValidationType.VALID_JWT;
        } catch (RuntimeException ex) {
            return JwtValidationType.INVALID_JWT;
        }
    }

    // JWT의 payload(Claims)를 추출하는 함수
    private Claims getBody(final String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public Long getUserFromJwt(String token) {
        Claims claims = getBody(token);
        return Long.valueOf(claims.get("memberId").toString());       // 클레임에서 memberId라는 이름의 클레임 값 추출 -> String으로 변 -> Long타입으로 변경
    }
}

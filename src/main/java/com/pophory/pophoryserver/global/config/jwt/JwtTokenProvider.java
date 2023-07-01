package com.pophory.pophoryserver.global.config.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Date;

@Component                          //이 클래스를 스프링의 빈으로 등록하기 위해 사용하는 어노테이션(의존성 관리, 생명주기 관리 등을 위해)
@RequiredArgsConstructor            //초기화 되지않은 final 필드나, @NonNull 이 붙은 필드에 대해 생성자를 생성해 줌
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

        //  getPrincipal: 인증 주체(principal)를 반환, 보통 인증된 사용자 의미. 주로 사용자의 식별자(ID)나 사용자 객체(User)를 포함
        claims.put("memberId", authentication.getPrincipal());

        return Jwts.builder()
                // setHeaderParam("typ", "JWT_TYPE")
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
            final Claims claims = getBody(token);           // 토큰으로부터 클래임 추출
            return JwtValidationType.VALID_JWT;
        } catch (MalformedJwtException ex) {                // JWT 토큰의 형식이 잘못되었을 때(문자열 형식이 올바르지 않을 때, base64로 인코딩 안됐을 때 등)
            return JwtValidationType.INVALID_JWT_TOKEN;
        } catch (ExpiredJwtException ex) {                  // JWT 유효기간 만료(1. 현재 시간이 토큰의 만료 시간을 확인한 경우, 2. JWT의 만료 클레임이 누락된 경우)
            return JwtValidationType.EXPIRED_JWT_TOKEN;
        } catch (UnsupportedJwtException ex) {              // 지원되지 않는 형식의 JWT(SecretKey를 이용해 암호화한 알고리즘이 시스템에서 지원되지 않는 경우)
            return JwtValidationType.UNSUPPORTED_JWT_TOKEN;
        } catch (IllegalArgumentException ex) {             // 매개변수로 잘못된 인자가 전달된 경우
            return JwtValidationType.EMPTY_JWT;
        }
    }

    // JWT의 payload(Claims)를 추출하는 함수
    private Claims getBody(final String token) {
        return Jwts.parserBuilder()             // 토큰을 파싱하기 위한 파서 생성
                .setSigningKey(getSigningKey())     //JWT 파서에 서명키 설정, 위에서 SecretKey로 만들어준 서명을 매개변수로 넣어준다.
                .build()                        // 파서 빌드
                .parseClaimsJwt(token)          // 토큰을 파싱하고 sign이 유효한지 확인
                .getBody();                     // JWT 토큰을 파싱하고, 서명을 확인하여 유효한 경우 토큰읜 payload(claims)를 반환
    }

    public Long getUserFromJwt(String token) {
        Claims claims = getBody(token);                 // 토큰으로부터 claims 추출
        return Long.parseLong(claims.get("memberId").toString());       // 클레임에서 memberId라는 이름의 클레임 값 추출 -> String으로 변 -> Long타입으로 변경
    }
}

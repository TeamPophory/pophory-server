package com.pophory.pophorycommon.config.jwt;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

// UsernamePasswordAuthenticationToken: 사용자의 인증 정보 저장하고 전달
// principal: 주체(사용자 ID), credentials: 자격 증명 정보(비밀번호, 토큰), authorities: 가지고 있는 권한 정보
public class UserAuthentication extends UsernamePasswordAuthenticationToken {

    // 사용자 인증 객체 생성
    public UserAuthentication(Object principal, Object credentials, Collection<? extends GrantedAuthority> authorities) {
        super(principal, credentials, authorities);
    }
}
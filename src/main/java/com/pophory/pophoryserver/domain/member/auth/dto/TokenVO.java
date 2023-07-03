package com.pophory.pophoryserver.domain.member.auth.dto;

import com.pophory.pophoryserver.domain.member.auth.dto.response.TokenResponseDto;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotBlank;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class TokenVO {
    @NotBlank
    private String accessToken;

    @NotBlank
    private String refreshToken;

    public static TokenVO of(String accessToken, String refreshToken) {
        return new TokenVO(accessToken, refreshToken);
    }
}

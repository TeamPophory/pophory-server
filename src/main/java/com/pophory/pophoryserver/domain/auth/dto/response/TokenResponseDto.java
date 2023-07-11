package com.pophory.pophoryserver.domain.auth.dto.response;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotBlank;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class TokenResponseDto {
    @NotBlank
    private String accessToken;

    @NotBlank
    private String refreshToken;

    public static TokenResponseDto of(String accessToken, String refreshToken) {
        return new TokenResponseDto(accessToken, refreshToken);
    }
}

package com.pophory.pophoryserver.domain.member.auth.dto.response;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class AuthResponseDto {

    @NotBlank
    private String accessToken;

    @NotBlank
    private String refreshToken;

    @NotNull
    private boolean isRegistered;

    public static AuthResponseDto of(String accessToken, String refreshToken, boolean isRegistered) {
        return new AuthResponseDto(accessToken, refreshToken, isRegistered);
    }
}

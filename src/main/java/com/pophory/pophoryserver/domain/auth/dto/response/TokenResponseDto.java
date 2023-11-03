package com.pophory.pophoryserver.domain.auth.dto.response;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;


@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class TokenResponseDto {

    @Schema(description = "포포리 액세스토큰")
    @NotBlank
    private String accessToken;

    @Schema(description = "포포리 리프레시토큰")
    @NotBlank
    private String refreshToken;

    public static TokenResponseDto of(String accessToken, String refreshToken) {
        return new TokenResponseDto(accessToken, refreshToken);
    }
}

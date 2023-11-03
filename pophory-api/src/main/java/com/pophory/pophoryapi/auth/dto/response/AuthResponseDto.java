package com.pophory.pophoryapi.auth.dto.response;

import com.pophory.pophoryapi.auth.dto.TokenVO;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;


@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Schema(description = "소셜 로그인 반환 DTO")
public class AuthResponseDto {

    @NotBlank
    @Schema(description = "포포리 액세스토큰")
    private String accessToken;

    @NotBlank
    @Schema(description = "포포리 리프레시토큰")
    private String refreshToken;

    @NotNull
    @Schema(description = "멤버가 등록되어있는지 여부", example = "true")
    private Boolean isRegistered;

    public static AuthResponseDto of(TokenVO tokenVO, boolean isRegistered) {
        return new AuthResponseDto(tokenVO.getAccessToken(), tokenVO.getRefreshToken(), isRegistered);
    }
}

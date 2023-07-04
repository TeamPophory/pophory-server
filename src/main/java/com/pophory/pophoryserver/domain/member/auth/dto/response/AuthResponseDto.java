package com.pophory.pophoryserver.domain.member.auth.dto.response;

import com.pophory.pophoryserver.domain.member.auth.dto.TokenVO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

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
    private boolean isRegistered;

    public static AuthResponseDto of(TokenVO tokenVO, boolean isRegistered) {
        return new AuthResponseDto(tokenVO.getAccessToken(), tokenVO.getRefreshToken(), isRegistered);
    }
}

package com.pophory.pophoryapi.auth.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Schema(description = "소셜 로그인 요청 DTO")
@NoArgsConstructor
public class AuthRequestDto {

    @NotBlank
    @Schema(description = "로그인한 소셜 서비스 정보", example = "KAKAO 또는 APPLE")
    private String socialType;
}

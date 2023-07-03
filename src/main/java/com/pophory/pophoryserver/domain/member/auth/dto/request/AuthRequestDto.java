package com.pophory.pophoryserver.domain.member.auth.dto.request;

import com.pophory.pophoryserver.domain.member.auth.SocialType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Schema(description = "소셜 로그인 요청 DTO")
@NoArgsConstructor
public class AuthRequestDto {

    @NotBlank
    @Schema(description = "로그인한 소셜 서비스 정보", example = "KAKAO, APPLE")
    private String socialType;
}

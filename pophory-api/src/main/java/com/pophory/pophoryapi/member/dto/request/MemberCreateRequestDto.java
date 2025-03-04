package com.pophory.pophoryapi.member.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Schema(description = "회원 가입 요청 DTO")
public class MemberCreateRequestDto {

    @NotBlank
    @Size(min = 2, max = 6)
    @Pattern(regexp = "^[가-힣]*$")
    @Schema(description = "회원 이름", example = "한수아")
    private String realName;

    @NotBlank
    @Size(min = 4, max = 12)
    @Pattern(regexp = "^[a-zA-Z0-9._]*$")
    @Schema(description = "회원 아이디", example = "pophory")
    private String nickname;

    @Schema(description = "앨범 커버 1 ~ 4 중의 값으로 요청", example = "1")
    private Long albumCover;
}

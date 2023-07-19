package com.pophory.pophoryserver.domain.member.dto.request;

import com.pophory.pophoryserver.domain.fcm.FcmOS;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Schema(description = "회원 가입 요청 DTO")
public class MemberCreateV2RequestDto {

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

    @Schema(description = "멤버의 fcmToken")
    private String fcmToken;

    @Schema(description = "OS 정보", example = "iOS 또는 AOS")
    private FcmOS fcmOS;
}

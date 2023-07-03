package com.pophory.pophoryserver.domain.member.dto.response;

import com.pophory.pophoryserver.domain.member.Member;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import static lombok.AccessLevel.*;

@AllArgsConstructor(access = PRIVATE)
@Getter
@Schema(description = "회원 정보")
public class MemberGetResponseDto {
    @Schema(description = "회원 고유 id", example = "1")
    private Long id;
    @Schema(description = "회원 실명", example = "강윤서")
    private String realName;
    @Schema(description = "회원 닉네임", example = "yundol")
    private String nickname;
    @Schema(description = "회원 프로필 이미지", example = "https://pophorycom/1.jpg")
    private String profileImageUrl;

    public static MemberGetResponseDto of(Member member) {
        return new MemberGetResponseDto(member.getId(), member.getRealName(), member.getNickname(), member.getProfileImage());
    }
}

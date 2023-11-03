package com.pophory.pophoryapi.member.dto.response;

import com.pophory.pophorydomain.member.Member;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

import static lombok.AccessLevel.PRIVATE;

@AllArgsConstructor(access = PRIVATE)
@Getter
@Schema(description = "마이페이지 회원 정보")
public class MemberMyPageGetV2ResponseDto {

    @Schema(description = "회원 고유 id", example = "1")
    private Long id;

    @Schema(description = "회원 실명", example = "강윤서")
    private String realName;

    @Schema(description = "회원 닉네임", example = "@yundol")
    private String nickname;

    @Schema(description = "[Optional] 회원 프로필 이미지", example = "https://pophorycom/1.jpg")
    private String profileImageUrl;

    @Schema(description = "그동안 찍은 사진", example = "10")
    private int photoCount;

    public static MemberMyPageGetV2ResponseDto of(Member member, int photoCount) {
        return new MemberMyPageGetV2ResponseDto(member.getId(), member.getRealName(), "@"+member.getNickname(), member.getProfileImage(), photoCount);
    }
}

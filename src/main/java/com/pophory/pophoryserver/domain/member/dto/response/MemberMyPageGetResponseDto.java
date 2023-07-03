package com.pophory.pophoryserver.domain.member.dto.response;

import com.pophory.pophoryserver.domain.member.Member;
import com.pophory.pophoryserver.domain.photo.dto.response.PhotoListGetResponseDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Schema(description = "회원 마이페이지 정보")
public class MemberMyPageGetResponseDto {
    @Schema(description = "회원 실명", example = "강윤서")
    private String realName;
    @Schema(description = "회원 닉네임", example = "yundol")
    private String nickname;
    @Schema(description = "회원 프로필 이미지", example = "https://pophorycom/1.jpg")
    private String profileImage;
    @Schema(description = "회원 앨범 수", example = "3")
    private int photoCount;
    private PhotoListGetResponseDto photos;

    public static MemberMyPageGetResponseDto of(Member member, int count, PhotoListGetResponseDto photos) {
        return new MemberMyPageGetResponseDto(member.getRealName(), member.getNickname(), member.getProfileImage(), count, photos);
    }

}

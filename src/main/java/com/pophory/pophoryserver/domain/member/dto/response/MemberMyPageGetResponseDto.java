package com.pophory.pophoryserver.domain.member.dto.response;

import com.pophory.pophoryserver.domain.member.Member;
import com.pophory.pophoryserver.domain.photo.dto.response.PhotoListGetResponseDto;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class MemberMyPageGetResponseDto {

    private String realName;
    private String nickname;
    private String profileImage;
    private int photoCount;
    private PhotoListGetResponseDto photos;

    public static MemberMyPageGetResponseDto of(Member member, int count, PhotoListGetResponseDto photos) {
        return new MemberMyPageGetResponseDto(member.getRealName(), member.getNickname(), member.getProfileImage(), count, photos);
    }

}

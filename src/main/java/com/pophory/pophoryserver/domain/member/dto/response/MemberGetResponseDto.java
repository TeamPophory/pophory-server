package com.pophory.pophoryserver.domain.member.dto.response;

import com.pophory.pophoryserver.domain.member.Member;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import static lombok.AccessLevel.*;

@AllArgsConstructor(access = PRIVATE)
@Getter
public class MemberGetResponseDto {
    private Long id;
    private String realName;
    private String nickname;
    private String profileImageUrl;

    public static MemberGetResponseDto of(Member member) {
        return new MemberGetResponseDto(member.getId(), member.getRealName(), member.getNickname(), member.getProfileImage());
    }
}

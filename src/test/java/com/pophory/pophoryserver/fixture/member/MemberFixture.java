package com.pophory.pophoryserver.fixture.member;

import com.pophory.pophoryserver.domain.auth.SocialType;
import com.pophory.pophoryserver.domain.member.Member;

public class MemberFixture {

    private static final String SOCIAL_ID = "socialId";
    private static final String NICKNAME = "nickname";
    private static final SocialType SOCIAL_TYPE = SocialType.KAKAO;

    public static Member createMember() {
        return Member.builder()
                .socialId(SOCIAL_ID)
                .socialType(SOCIAL_TYPE)
                .build();
    }
}

package com.pophory.pophoryserver.domain.member;

import com.pophory.pophoryserver.config.TestConfig;
import com.pophory.pophoryserver.domain.auth.SocialType;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;


import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@ActiveProfiles("test")
@DataJpaTest
@RequiredArgsConstructor
@Import(TestConfig.class)
public class MemberTest {

    @Autowired
    private MemberJpaRepository memberJpaRepository;

   @Nested
   @DisplayName("회원 등록 테스트")
    public class MemberRegisterTest {

       @Test
       @Transactional
       @DisplayName("소셜로그인 후에 멤버가 성공적으로 만들어진다.")
       void successRegisterMember() {
           Member member = Member.builder()
                   .socialId("123456789")
                   .socialType(SocialType.KAKAO)
                   .build();
           memberJpaRepository.save(member);
           assertThat(member.getSocialId()).isEqualTo("123456789");
           assertThat(member.getSocialType()).isEqualTo(SocialType.KAKAO);

           memberJpaRepository.findBySocialId("123456789")
                   .ifPresent(m -> {
                       assertThat(m.getSocialId()).isEqualTo("123456789");
                       assertThat(m.getSocialType()).isEqualTo(SocialType.KAKAO);
                   });
       }
   }

}

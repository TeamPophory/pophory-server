package com.pophory.pophoryserver.domain.member;

import com.pophory.pophoryserver.domain.auth.SocialType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberJpaRepository extends JpaRepository<Member, Long> {
    boolean existsMemberByNickname(String nickname);
    boolean existsMemberBySocialIdAndSocialType(String socialId, SocialType socialType);
    Optional<Member> getMemberBySocialIdAndSocialType(String socialId, SocialType socialType);
}

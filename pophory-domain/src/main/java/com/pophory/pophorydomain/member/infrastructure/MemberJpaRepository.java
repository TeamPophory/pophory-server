package com.pophory.pophorydomain.member.infrastructure;

import com.pophory.pophorydomain.member.Member;
import com.pophory.pophorydomain.member.SocialType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberJpaRepository extends JpaRepository<Member, Long> {

    boolean existsMemberByNickname(String nickname);
    boolean existsMemberBySocialIdAndSocialType(String socialId, SocialType socialType);

    Optional<Member> getMemberBySocialIdAndSocialType(String socialId, SocialType socialType);

    Optional<Member> findByNickname(String nickname);

    Optional<Member> findBySocialId(String socialId);
}

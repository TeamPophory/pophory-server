package com.pophory.pophoryserver.domain.member;


import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import static com.pophory.pophoryserver.domain.member.QMember.*;

@Repository
@RequiredArgsConstructor
public class MemberQueryRepository {

    private final JPAQueryFactory queryFactory;

    public Member findMemberById(Long id) {
        return queryFactory.select(member)
                .from(member)
                .where(member.id.eq(id))
                .fetchOne();
    }
}

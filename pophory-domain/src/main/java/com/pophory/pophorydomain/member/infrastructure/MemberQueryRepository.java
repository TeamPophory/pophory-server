package com.pophory.pophorydomain.member.infrastructure;


import com.pophory.pophorydomain.member.Member;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import static com.pophory.pophorydomain.member.QMember.member;


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

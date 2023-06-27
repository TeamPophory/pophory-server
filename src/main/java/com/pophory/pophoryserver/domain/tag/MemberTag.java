package com.pophory.pophoryserver.domain.tag;

import com.pophory.pophoryserver.domain.member.Member;
import com.pophory.pophoryserver.domain.photo.Photo;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static javax.persistence.FetchType.*;

@Entity
@NoArgsConstructor
public class MemberTag {

    @Id @GeneratedValue
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "photo_id")
    private Photo photo;

    @OneToOne(fetch = LAZY)
    @JoinColumn(name = "member_id")
    private Member member;
}

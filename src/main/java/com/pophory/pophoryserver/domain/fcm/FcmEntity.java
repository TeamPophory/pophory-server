package com.pophory.pophoryserver.domain.fcm;

import com.pophory.pophoryserver.domain.member.Member;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static javax.persistence.EnumType.STRING;
import static javax.persistence.FetchType.*;

@Table(name = "fcm")
@Entity
@NoArgsConstructor
@Getter
public class FcmEntity {

    @Id @GeneratedValue
    private Long id;
    private String fcmToken;

    @Enumerated(value = STRING)
    @Column(name = "fcm_os")
    private FcmOS fcmOS;

    @JoinColumn(name = "member_id")
    @ManyToOne(fetch = LAZY)
    private Member member;
}

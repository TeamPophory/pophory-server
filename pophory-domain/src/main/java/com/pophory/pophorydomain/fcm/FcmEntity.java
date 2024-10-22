package com.pophory.pophorydomain.fcm;

import com.pophory.pophorydomain.member.Member;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static jakarta.persistence.EnumType.STRING;
import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.IDENTITY;


@Table(name = "fcm")
@Entity
@NoArgsConstructor
@Getter
public class FcmEntity {

    @Id @GeneratedValue(strategy = IDENTITY)
    private Long id;
    private String fcmToken;

    @Enumerated(value = STRING)
    @Column(name = "fcm_os")
    private FcmOS fcmOS;

    @JoinColumn(name = "member_id")
    @ManyToOne(fetch = LAZY)
    private Member member;

    @Builder
    public FcmEntity(String fcmToken, FcmOS fcmOS, Member member) {
        this.fcmToken = fcmToken;
        this.fcmOS = fcmOS;
        this.member = member;
    }
}

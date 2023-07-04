package com.pophory.pophoryserver.domain.member;


import com.pophory.pophoryserver.domain.album.Album;
import com.pophory.pophoryserver.domain.member.auth.SocialType;
import com.pophory.pophoryserver.global.entity.BaseTimeEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.EnumType.*;

@Entity
@Getter
@NoArgsConstructor
public class Member extends BaseTimeEntity {

    @Id @GeneratedValue
    private Long id;

    @Column(length = 6)
    private String realName;

    @Column(length = 15)
    private String nickname;

    private String profileImage;

    @Enumerated(value = STRING)
    private SocialType socialType;

    private String socialId;

    private String refreshToken;

    private String fcmToken;

    private boolean isDeleted = false;
    private LocalDateTime deletedAt;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<Album> albumList = new ArrayList<>();

    public void softDelete() {
        this.isDeleted = true;
        this.deletedAt = LocalDateTime.now();
    }

    public void updateRealName(String realName) {
        this.realName = realName;
    }

    public void updateNickname(String nickname) {
        this.nickname = nickname;
    }

    public void updateRefreshToken(String refreshToken) { this.refreshToken = refreshToken; }

    @Builder
    public Member(String socialId, SocialType socialType) {
        this.socialId = socialId;
        this.socialType = socialType;
    }
}

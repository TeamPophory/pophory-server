package com.pophory.pophorydomain.member;



import com.pophory.pophorycommon.exception.MemberException;
import com.pophory.pophorydomain.album.Album;
import com.pophory.pophorydomain.common.BaseTimeEntity;
import com.pophory.pophorydomain.fcm.FcmEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static jakarta.persistence.EnumType.STRING;
import static jakarta.persistence.GenerationType.IDENTITY;


@Entity
@Getter
@NoArgsConstructor
public class Member extends BaseTimeEntity {

    private static final int MEMBER_DELETE_EXPIRE_TIME = 7;

    @Id @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Column(length = 6)
    private String realName;

    @Column(length = 15, unique = true)
    private String nickname;

    private String profileImage;

    @Enumerated(value = STRING)
    private SocialType socialType;

    @NotNull(message = "소셜 아이디는 필수입니다.")
    private String socialId;

    private String refreshToken;

    private String fcmToken;

    private String pophoryId;

    private boolean isDeleted = false;
    private LocalDateTime deletedAt;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<Album> albumList = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<FcmEntity> fcmList = new ArrayList<>();

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

    public void generatePophoryId(String pophoryId) {
        this.pophoryId = pophoryId;
    }

    public boolean checkSignUpUpdated() {
        return !(Objects.isNull(this.nickname) || Objects.isNull(this.realName));
    }

    @Builder
    private Member(String socialId, SocialType socialType) {
        this.socialId = socialId;
        this.socialType = socialType;
    }
}

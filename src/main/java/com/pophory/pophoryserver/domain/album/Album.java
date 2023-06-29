package com.pophory.pophoryserver.domain.album;

import com.pophory.pophoryserver.domain.member.Member;
import com.pophory.pophoryserver.global.entity.BaseTimeEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

import static javax.persistence.FetchType.*;

@Entity
@Getter
@NoArgsConstructor
public class Album extends BaseTimeEntity {

    @Id @GeneratedValue
    private Long id;

    private String title = "기본 앨범";

    private int cover = 0;

    private String imageUrl;

    private boolean isDeleted = false;
    private LocalDateTime deletedAt;

    @JoinColumn(name = "member_id")
    @ManyToOne(fetch = LAZY)
    private Member member;

    public void softDelete() {
        this.isDeleted = true;
        this.deletedAt = LocalDateTime.now();
    }

    public void setCover(int cover) {
        this.cover = cover;
    }
}

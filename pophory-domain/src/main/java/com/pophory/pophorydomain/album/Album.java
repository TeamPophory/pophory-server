package com.pophory.pophorydomain.album;


import com.pophory.pophorydomain.albumtheme.AlbumDesign;
import com.pophory.pophorydomain.common.BaseTimeEntity;
import com.pophory.pophorydomain.member.Member;
import com.pophory.pophorydomain.photo.Photo;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.IDENTITY;


@Entity
@Getter
@NoArgsConstructor
public class Album extends BaseTimeEntity {
    private static final int MIN_PHOTO_LIMIT = 1;

    @Id @GeneratedValue(strategy = IDENTITY)
    private Long id;

    private String title = "기본 앨범";

    private Long albumDesignId;

    @Min(value = 1, message = "사진제한은 1장 이상이어야 합니다.")
    private int photoLimit;

    private String imageUrl;

    private boolean isDeleted = false;
    private LocalDateTime deletedAt;

    @JoinColumn(name = "member_id")
    @ManyToOne(fetch = LAZY)
    private Member member;

    @OneToMany(mappedBy = "album", cascade = CascadeType.ALL)
    private final List<Photo> photoList = new ArrayList<>();

    public void softDelete() {
        this.isDeleted = true;
        this.deletedAt = LocalDateTime.now();
    }

    @Builder
    public Album(String title, AlbumDesign albumDesign, int photoLimit, String imageUrl, Member member) {
        this.title = title;
        this.albumDesignId = albumDesign.getId();
        this.photoLimit = photoLimit;
        this.imageUrl = imageUrl;
        this.member = member;
    }

    private void validatePhotoLimit(int photoLimit) {
        if (this.photoLimit < 1) throw new IllegalArgumentException("사진 제한은 1장 이상이어야 합니다.");
    }

    public void updateAlbumDesign(AlbumDesign albumDesign) {
        this.albumDesignId = albumDesign.getId();
    }

    public void updateMember(Member member) {
        this.member = member;
    }

    public void updatePhotoLimit(int photoLimit) {
        if (photoLimit < 1) throw new IllegalArgumentException("사진 제한은 1장 이상이어야 합니다.");
        this.photoLimit = photoLimit;
    }

    public boolean checkPhotoLimit() {
        return this.photoLimit > this.photoList.size();
    }
}
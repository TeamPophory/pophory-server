package com.pophory.pophorydomain.album;


import com.pophory.pophorycommon.entity.BaseTimeEntity;
import com.pophory.pophorydomain.albumtheme.AlbumDesign;
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


@Entity
@Getter
@NoArgsConstructor
public class Album extends BaseTimeEntity {

    @Id @GeneratedValue
    private Long id;

    private String title = "기본 앨범";

    @JoinColumn(name = "album_design_id")
    @OneToOne(fetch = LAZY)
    private AlbumDesign albumDesign;

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
        this.albumDesign = albumDesign;
        this.photoLimit = photoLimit;
        this.imageUrl = imageUrl;
        this.member = member;
    }



    public void setAlbumDesign(AlbumDesign albumDesign) {
        this.albumDesign = albumDesign;
    }
    public void setMember(Member member) { this.member = member; }

    public void setPhotoLimit(int photoLimit) {
        this.photoLimit = photoLimit;
    }

    public boolean checkPhotoLimit() {
        return this.photoLimit > this.photoList.size();
    }
}
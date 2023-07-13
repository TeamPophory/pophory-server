package com.pophory.pophoryserver.domain.photo;

import com.pophory.pophoryserver.domain.album.Album;
import com.pophory.pophoryserver.domain.photo.vo.PhotoSizeVO;
import com.pophory.pophoryserver.domain.studio.Studio;
import com.pophory.pophoryserver.global.entity.BaseTimeEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

import static javax.persistence.FetchType.*;

@Entity
@NoArgsConstructor
@Getter
public class Photo extends BaseTimeEntity {

    @Id @GeneratedValue
    private Long id;

    private String imageUrl;

    private boolean isDeleted;
    private LocalDateTime deletedAt;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "album_id")
    private Album album;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "studio_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private Studio studio;

    private LocalDate takenAt;

    private int width;

    private int height;

    @Builder
    public Photo(String imageUrl, Album album, Studio studio, LocalDate takenAt, PhotoSizeVO photoSizeVO) {
        this.imageUrl = imageUrl;
        this.album = album;
        this.studio = studio;
        this.takenAt = takenAt;
        this.isDeleted = false;
        this.width = photoSizeVO.getWidth();
        this.height = photoSizeVO.getHeight();
    }

    public void softDelete() {
        this.isDeleted = true;
        this.deletedAt = LocalDateTime.now();
    }

    private void setAlbum(Album album) {
        if (Objects.nonNull(this.album)) {
            this.album.getPhotoList().remove(this);
        }
        this.album = album;
        album.getPhotoList().add(this);
    }
}


package com.pophory.pophoryserver.domain.photo;

import com.pophory.pophoryserver.domain.album.Album;
import com.pophory.pophoryserver.domain.studio.Studio;
import com.pophory.pophoryserver.domain.tag.GuestTag;
import com.pophory.pophoryserver.domain.tag.MemberTag;
import com.pophory.pophoryserver.global.entity.BaseTimeEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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
    @JoinColumn(name = "studio_id")
    private Studio studio;

    private LocalDate takenAt;

    @OneToMany(mappedBy = "photo", cascade = CascadeType.ALL)
    private final List<MemberTag> memberTagList = new ArrayList<>();

    @OneToMany(mappedBy = "photo", cascade = CascadeType.ALL)
    private final List<GuestTag> guestTagList = new ArrayList<>();

    @Builder
    public Photo(String imageUrl, Album album, Studio studio, LocalDate takenAt) {
        this.imageUrl = imageUrl;
        this.album = album;
        this.studio = studio;
        this.takenAt = takenAt;
        this.isDeleted = false;
    }

    public void softDelete() {
        this.isDeleted = true;
        this.deletedAt = LocalDateTime.now();
    }
}


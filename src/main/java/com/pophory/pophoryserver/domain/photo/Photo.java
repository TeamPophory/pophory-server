package com.pophory.pophoryserver.domain.photo;

import com.pophory.pophoryserver.domain.album.Album;
import com.pophory.pophoryserver.domain.studio.Studio;
import com.pophory.pophoryserver.global.entity.BaseTimeEntity;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

import static javax.persistence.FetchType.*;

@Entity
@NoArgsConstructor
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

    public void softDelete() {
        this.isDeleted = true;
        this.deletedAt = LocalDateTime.now();
    }
}


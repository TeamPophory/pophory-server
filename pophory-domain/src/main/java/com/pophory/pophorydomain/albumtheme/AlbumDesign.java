package com.pophory.pophorydomain.albumtheme;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Entity
@NoArgsConstructor
@Getter
public class AlbumDesign {

    @Id @GeneratedValue
    private Long id;

    @JoinColumn(name = "album_cover_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private AlbumCover albumCover;

    private String imageUrl;
}

package com.pophory.pophoryserver.domain.albumtheme;


import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Getter
public class AlbumDesign {

    @Id @GeneratedValue
    private Long id;

    @JoinColumn(name = "album_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private AlbumCover albumCover;

    private String imageUrl;
}

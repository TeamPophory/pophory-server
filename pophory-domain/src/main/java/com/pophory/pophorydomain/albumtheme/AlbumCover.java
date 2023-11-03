package com.pophory.pophorydomain.albumtheme;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.EnumType.STRING;


@Entity
@Table(name = "album_cover")
@NoArgsConstructor
@Getter
public class AlbumCover {

    @Id
    @GeneratedValue
    private Long id;

    private int coverNumber;

    private String imageUrl;

    @Enumerated(STRING)
    private AlbumTheme theme;

    @OneToMany(mappedBy = "albumCover", cascade = CascadeType.ALL)
    private List<AlbumDesign> albumDesignList = new ArrayList<>();

    @Builder
    public AlbumCover(int coverNumber) {
        this.coverNumber = coverNumber;
    }
}

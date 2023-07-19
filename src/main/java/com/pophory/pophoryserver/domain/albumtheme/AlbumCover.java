package com.pophory.pophoryserver.domain.albumtheme;

import com.pophory.pophoryserver.domain.album.Album;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import java.util.ArrayList;
import java.util.List;

import static javax.persistence.EnumType.STRING;
import static javax.persistence.FetchType.*;

@Entity
@Table(name = "album_cover")
@NoArgsConstructor
@Getter
public class AlbumCover {

    @Id @GeneratedValue
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

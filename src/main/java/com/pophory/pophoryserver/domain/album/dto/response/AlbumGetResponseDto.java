package com.pophory.pophoryserver.domain.album.dto.response;

import com.pophory.pophoryserver.domain.album.Album;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AlbumGetResponseDto {
    private Long id;
    private String title;
    private int photoCount;

    public static AlbumGetResponseDto of(Album album, int count) {
        return new AlbumGetResponseDto(album.getId(), album.getTitle(), count);
    }
}

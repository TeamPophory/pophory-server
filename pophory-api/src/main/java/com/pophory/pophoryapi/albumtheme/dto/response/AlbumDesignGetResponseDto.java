package com.pophory.pophoryapi.albumtheme.dto.response;

import com.pophory.pophorydomain.albumtheme.AlbumDesign;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AlbumDesignGetResponseDto {

    private Long designId;
    private String theme;
    private String imageUrl;

    public static AlbumDesignGetResponseDto of(AlbumDesign albumDesign) {
        return new AlbumDesignGetResponseDto(albumDesign.getId(), albumDesign.getAlbumCover().getTheme().getName(), albumDesign.getImageUrl());
    }
}

package com.pophory.pophoryserver.domain.photo.dto.response;

import com.pophory.pophoryserver.domain.album.Album;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PhotoShareApproveResponseDto {
    private Long albumId;

    public static PhotoShareApproveResponseDto of(Album album) {
        return new PhotoShareApproveResponseDto(album.getId());
    }
    
}

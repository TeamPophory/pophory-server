package com.pophory.pophoryapi.album.dto.response;

import com.pophory.pophorydomain.album.Album;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Schema(description = "앨범 정보")
public class AlbumGetV2ResponseDto {
    @Schema(description = "앨범 고유 id", example = "1")
    private Long id;
    @Schema(description = "앨범 제목", example = "강윤서의 앨범")
    private String title;
    @Schema(description = "앨범 디자인", example = "1")
    private Long albumCover;
    @Schema(description = "앨범 사진 수", example = "3")
    private int photoCount;
    @Schema(description = "앨범 사진 한도", example = "15")
    private int photoLimit;

    public static AlbumGetV2ResponseDto of(Album album, int count) {
        return new AlbumGetV2ResponseDto(album.getId(), album.getTitle(), album.getAlbumDesignId(), count, album.getPhotoLimit());
    }
}

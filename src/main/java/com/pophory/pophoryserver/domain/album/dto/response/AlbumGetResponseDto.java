package com.pophory.pophoryserver.domain.album.dto.response;

import com.pophory.pophoryserver.domain.album.Album;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Schema(description = "앨범 정보")
public class AlbumGetResponseDto {
    @Schema(description = "앨범 고유 id", example = "1")
    private Long id;
    @Schema(description = "앨범 제목", example = "강윤서의 앨범")
    private String title;
    @Schema(description = "앨범 사진 수", example = "3")
    private int photoCount;

    public static AlbumGetResponseDto of(Album album, int count) {
        return new AlbumGetResponseDto(album.getId(), album.getTitle(), count);
    }
}

package com.pophory.pophoryserver.domain.album.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor(staticName = "of")
@Getter
@Schema(description = "앨범 리스트")
public class AlbumListGetResponseDto {
    @Schema(description = "앨범 리스트")
    private List<AlbumGetResponseDto> albums;
}

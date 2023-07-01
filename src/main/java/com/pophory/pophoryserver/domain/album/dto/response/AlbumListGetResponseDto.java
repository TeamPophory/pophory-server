package com.pophory.pophoryserver.domain.album.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor(staticName = "of")
@Getter
public class AlbumListGetResponseDto {
    private List<AlbumGetResponseDto> albums;
}

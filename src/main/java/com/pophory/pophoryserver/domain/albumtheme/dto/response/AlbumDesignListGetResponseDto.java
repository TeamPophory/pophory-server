package com.pophory.pophoryserver.domain.albumtheme.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@AllArgsConstructor(staticName = "of")
@Data
public class AlbumDesignListGetResponseDto {
    private List<AlbumDesignGetResponseDto> albumDesigns;
}

package com.pophory.pophoryapi.albumtheme.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@AllArgsConstructor(staticName = "of")
@Data
public class AlbumDesignsGetResponseDto {
    private List<AlbumDesignGetResponseDto> albumDesigns;
}

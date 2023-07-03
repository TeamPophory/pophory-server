package com.pophory.pophoryserver.domain.photo.dto.response;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor(staticName = "of")
@Getter
@Schema(description = "사진 리스트")
public class PhotoListGetResponseDto {
    @Schema(description = "사진 리스트")
    private List<PhotoGetResponseDto> photos;
}

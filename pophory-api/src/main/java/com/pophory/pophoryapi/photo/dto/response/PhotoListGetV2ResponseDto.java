package com.pophory.pophoryapi.photo.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor(staticName = "of")
@Schema(description = "사진 리스트")
public class PhotoListGetV2ResponseDto {
    @Schema(description = "사진 리스트")
    private List<PhotoGetV2ResponseDto> photos;
}

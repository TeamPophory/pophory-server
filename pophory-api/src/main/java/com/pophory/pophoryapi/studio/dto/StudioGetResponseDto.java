package com.pophory.pophoryapi.studio.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor(staticName = "of")
@Getter
@Schema(description = "사진관 리스트")
public class StudioGetResponseDto {

    @Schema(description = "사진관 리스트")
    List<StudioV2ResponseDto> studios;
}

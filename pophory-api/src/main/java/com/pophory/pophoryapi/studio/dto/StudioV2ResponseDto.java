package com.pophory.pophoryapi.studio.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
@Schema(description = "사진관 정보")
public class StudioV2ResponseDto {

    @Schema(description = "사진관 아이디")
    private Long id;

    @Schema(description = "사진관 아이디")
    private String name;

    @Builder
    public StudioV2ResponseDto(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}

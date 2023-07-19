package com.pophory.pophoryserver.domain.album.dto.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@Schema(description = "회원 가입 요청 DTO")
public class AlbumDesignUpdateRequestDto {

    @Schema(description = "앨범 디자인", example = "1")
    private Long albumDesign;

    @JsonCreato
    private AlbumDesignUpdateRequestDto(Long albumDesign) {
        this.albumDesign = albumDesign;
    }
}

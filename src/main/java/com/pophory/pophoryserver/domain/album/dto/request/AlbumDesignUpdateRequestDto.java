package com.pophory.pophoryserver.domain.album.dto.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@Schema(description = "앨범 디자인 수정 DTO")
public class AlbumDesignUpdateRequestDto {

    @Schema(description = "앨범 디자인 id", example = "1")
    private Long albumDesignId;

    @JsonCreator
    private AlbumDesignUpdateRequestDto(Long albumDesignId) {
        this.albumDesignId = albumDesignId;
    }
}

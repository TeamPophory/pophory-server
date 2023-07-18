package com.pophory.pophoryserver.domain.photo.dto.response;

import com.pophory.pophoryserver.domain.photo.Photo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Schema(description = "사진 전체 조회")
public class PhotoAllListV2ResponseDto {

    @Schema(description = "사진 id", example = "1")
    private Long photoId;

    @Schema(description = "사진 url", example = "https://pophorycom/1.jpg")
    private String photoUrl;

    @Builder
    public PhotoAllListV2ResponseDto(Long photoId, String photoUrl) {
        this.photoId = photoId;
        this.photoUrl = photoUrl;
    }
}

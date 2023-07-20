package com.pophory.pophoryserver.domain.pophorysm.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class PophorysmShareRequestDto {

    @Schema(description = "사진 id", example = "")
    private Long photoId;

    @Schema(description = "유저 닉네임", example = "")
    private String nickname;

    @Schema(description = "파일 이름", example = "")
    private String fileName;

    @Schema(description = "포포리 id", example = "")
    private String pophoryId;

    @Schema(description = "사진 가로 길이", example = "600")
    private Integer width;

    @Schema(description = "사진 세로 길이", example = "800")
    private Integer height;
}

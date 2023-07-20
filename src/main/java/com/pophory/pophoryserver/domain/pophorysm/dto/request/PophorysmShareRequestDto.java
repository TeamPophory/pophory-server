package com.pophory.pophoryserver.domain.pophorysm.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class PophorysmShareRequestDto {

    @Schema(description = "사진 id", example = "10")
    private Long photoId;

    @Schema(description = "유저 닉네임", example = "kraken")
    private String nickname;

    @Schema(description = "파일 이름", example = "ca4f3c69-a7ac-45ee-a594-d0567a4b66b2.jpg")
    private String fileName;

    @Schema(description = "포포리 id", example = "2d3dfa")
    private String pophoryId;

    @Schema(description = "사진 가로 길이", example = "600")
    private Integer width;

    @Schema(description = "사진 세로 길이", example = "800")
    private Integer height;
}
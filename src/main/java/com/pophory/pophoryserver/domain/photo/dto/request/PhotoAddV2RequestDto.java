package com.pophory.pophoryserver.domain.photo.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
@Schema(description = "사진 추가 요청 DTO")
public class PhotoAddV2RequestDto {

    @Schema(description = "사진 파일이름", example = "ca4f3c69-a7ac-45ee-a594-d0567a4b66b2.jpg")
    private String fileName;

    @Schema(description = "앨범 id", example = "1")
    private Long albumId;

    @Schema(description = "사진 찍은 날짜", example = "2023.06.30")
    @JsonFormat(pattern = "yyyy.MM.dd", timezone = "Asia/Seoul")
    private String takenAt;

    @Schema(description = "사진관 id", example = "1")
    private Long studioId;

    @Schema(description = "사진 width", example = "600")
    private Integer width;

    @Schema(description = "사진 height", example = "800")
    private Integer height;
}
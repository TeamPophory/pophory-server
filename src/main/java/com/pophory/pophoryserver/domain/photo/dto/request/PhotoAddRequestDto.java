package com.pophory.pophoryserver.domain.photo.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;


@AllArgsConstructor
@Getter
@Schema(description = "사진 추가 요청 DTO")
public class PhotoAddRequestDto {

    @Schema(description = "앨범 id", example = "1")
    private Long albumId;

    @Schema(description = "사진 찍은 날짜", example = "2023.06.30")
    @JsonFormat(pattern = "yyyy.MM.dd", timezone = "Asia/Seoul")
    private String takenAt;

    @Schema(description = "사진관 id", example = "1")
    private Long studioId;
}
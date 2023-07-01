package com.pophory.pophoryserver.domain.photo.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;


@AllArgsConstructor
@Getter
public class PhotoAddRequestDto {

    private Long albumId;

    @JsonFormat(pattern = "yyyy.MM.dd", timezone = "Asia/Seoul")
    private String takenAt;

    private Long studioId;
}

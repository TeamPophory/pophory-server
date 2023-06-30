package com.pophory.pophoryserver.domain.photo.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotBlank;

@AllArgsConstructor
@Getter
public class PhotoAddRequestDto {

    private Long albumId;

    private String date;

    private Long studioId;
}

package com.pophory.pophoryserver.domain.photo.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor(staticName = "of")
@Getter
public class PhotoListGetResponseDto {
    private List<PhotoGetResponseDto> photos;
}

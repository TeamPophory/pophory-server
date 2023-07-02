package com.pophory.pophoryserver.domain.photo.dto.response;

import com.pophory.pophoryserver.domain.photo.Photo;
import com.pophory.pophoryserver.global.util.PhotoUtil;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class PhotoGetResponseDto {
    private Long id;
    private String studio;
    private String takenAt;
    private String imageUrl;

    public static PhotoGetResponseDto of(Photo photo) {
        return new PhotoGetResponseDto(photo.getId(), photo.getStudio().getName(), PhotoUtil.changeTakenAtToResponseFormat(photo.getTakenAt()), photo.getImageUrl());
    }
}
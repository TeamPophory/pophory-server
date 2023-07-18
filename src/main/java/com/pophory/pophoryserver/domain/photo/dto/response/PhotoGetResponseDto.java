package com.pophory.pophoryserver.domain.photo.dto.response;

import com.pophory.pophoryserver.domain.photo.Photo;
import com.pophory.pophoryserver.global.util.PhotoUtil;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;
import java.util.Optional;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Schema(description = "사진 정보")
public class PhotoGetResponseDto {
    @Schema(description = "사진 고유 id", example = "1")
    private Long id;
    
    @Schema(description = "스튜디오 이름", example = "인생 네컷")
    private String studio;

    @Schema(description = "촬영 날짜", example = "2023.06.30")
    private String takenAt;

    @Schema(description = "사진 url", example = "https://pophorycom/1.jpg")
    private String imageUrl;

    @Schema(description = "사진 가로 길이", example = "1920")
    private int width;

    @Schema(description = "사진 세로 길이", example = "1080")
    private int height;

    public static PhotoGetResponseDto of(Photo photo)  {
        String studioName = photo.getStudio() != null ? photo.getStudio().getName() : "NONE";
        return new PhotoGetResponseDto(photo.getId(), studioName, PhotoUtil.changeTakenAtToResponseFormat(photo.getTakenAt()), photo.getImageUrl(), photo.getWidth(), photo.getHeight());
    }

    public static LocalDate getLocalDateTakenAt(PhotoGetResponseDto photoGetResponseDto) {
        return PhotoUtil.changeRequestToTakenAt(photoGetResponseDto.getTakenAt());
    }

}
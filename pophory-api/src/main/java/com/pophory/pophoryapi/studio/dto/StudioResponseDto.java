package com.pophory.pophoryapi.studio.dto;

import com.pophory.pophorydomain.studio.Studio;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Schema(description = "사진관 목록")
public class StudioResponseDto {

    @Schema(description = "사진관 리스트")
    private List<Studio> studios;

    public static StudioResponseDto of(List<Studio> studios) {
        return new StudioResponseDto(studios);
    }
}
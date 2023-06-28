package com.pophory.pophoryserver.domain.studio.dto;

import com.pophory.pophoryserver.domain.studio.Studio;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class StudioResponseDto {

    private List<Studio> studios;

    public static StudioResponseDto of(List<Studio> studios) {
        return new StudioResponseDto(studios);

    }
}

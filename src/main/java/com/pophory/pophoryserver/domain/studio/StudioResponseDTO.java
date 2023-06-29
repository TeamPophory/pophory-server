package com.pophory.pophoryserver.domain.studio;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class StudioResponseDTO {

    private List<Studio> studios;

    public static StudioResponseDTO of(List<Studio> studios) {
        return new StudioResponseDTO(studios);

    }
}

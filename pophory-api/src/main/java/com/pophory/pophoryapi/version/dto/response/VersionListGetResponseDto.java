package com.pophory.pophoryapi.version.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;


@AllArgsConstructor
@Data
public class VersionListGetResponseDto {
    List<VersionGetResponseDto> versions;

    public static VersionListGetResponseDto of(List<VersionGetResponseDto> versionGetResponseDtos) {
        return new VersionListGetResponseDto(versionGetResponseDtos);
    }
}
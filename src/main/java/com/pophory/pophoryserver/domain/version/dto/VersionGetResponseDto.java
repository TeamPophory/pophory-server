package com.pophory.pophoryserver.domain.version.dto;

import com.pophory.pophoryserver.domain.version.OSType;
import com.pophory.pophoryserver.domain.version.ServiceVersion;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;


@Getter
@AllArgsConstructor
public class VersionGetResponseDto {

    @Schema(description = "운영 체제", example = "IOS, AOS")
    private OSType os;

    @Schema(description = "버전", example = "1.0.0")
    private String version;

    public static VersionGetResponseDto of(ServiceVersion serviceVersion) {
        return new VersionGetResponseDto(serviceVersion.getOsType(), serviceVersion.getVersion());
    }
}

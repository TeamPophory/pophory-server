package com.pophory.pophoryserver.domain.version.dto;

import com.pophory.pophoryserver.domain.version.OSType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class VersionUpdateRequestDto {

    @Schema(description = "운영 체제", example = "IOS, AOS")
    private OSType os;
    @Schema(description = "버전", example = "1.0.0")
    private String version;
}

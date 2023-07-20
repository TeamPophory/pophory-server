package com.pophory.pophoryserver.domain.version.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor(staticName = "of")
@Data
public class PophorysmGetPresignedUrlResponseDto {
    private String presignedUrl;
    private String fileName;
    private String pophoryId;
}

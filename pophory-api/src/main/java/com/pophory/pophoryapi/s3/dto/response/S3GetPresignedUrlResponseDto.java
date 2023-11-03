package com.pophory.pophoryapi.s3.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor(staticName = "of")
public class S3GetPresignedUrlResponseDto {
    @Schema(description = "사진 업로드 url", example = "https://pophory.s3.ap-northeast-2.amazonaws.com/...")
    private String presignedUrl;
    @Schema(description = "사진 파일 이름", example = "ca4f3c69-a7ac-45ee-a594-d0567a4b66b2.jpg")
    private String fileName;
}

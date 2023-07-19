package com.pophory.pophoryserver.domain.s3;


import com.pophory.pophoryserver.domain.photo.service.PhotoService;
import com.pophory.pophoryserver.domain.s3.dto.response.S3GetPresignedUrlResponseDto;
import com.pophory.pophoryserver.global.util.MemberUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

import static org.springframework.http.MediaType.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v2/s3")
@Tag(name = "[S3] S3 관련 API (V2)")
public class S3Controller {

    private final PhotoService photoService;
    @GetMapping(value = "/photo", produces = APPLICATION_JSON_VALUE)
    @SecurityRequirement(name = "Authorization")
    @Operation(summary = "사진 업로드 url 생성 API")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "사진 업로드 url 생성 성공"),
                    @ApiResponse(responseCode = "400", description = "사진 업로드 url 생성 실패", content = @Content),
                    @ApiResponse(responseCode = "500", description = "서버 오류", content = @Content)
            }
    )
    public ResponseEntity<S3GetPresignedUrlResponseDto> getUploadPhotoPresignedUrl(Principal principal) {
        return ResponseEntity.ok(photoService.getPresignedUrl(UploadType.PHOTO, MemberUtil.getMemberId(principal)));
    }

    @GetMapping(value = "/profile", produces = APPLICATION_JSON_VALUE)
    @SecurityRequirement(name = "Authorization")
    @Operation(summary = "사진 업로드 url 생성 API")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "프로필 사진 업로드 url 생성 성공"),
                    @ApiResponse(responseCode = "400", description = "프로필 사진 업로드 url 생성 실패", content = @Content),
                    @ApiResponse(responseCode = "500", description = "서버 오류", content = @Content)
            }
    )
    public ResponseEntity<S3GetPresignedUrlResponseDto> getUploadProfilePresignedUrl(Principal principal) {
        return ResponseEntity.ok(photoService.getPresignedUrl(UploadType.PROFILE, MemberUtil.getMemberId(principal)));
    }
}
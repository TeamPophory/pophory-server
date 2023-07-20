package com.pophory.pophoryserver.domain.pophorysm;

import com.pophory.pophoryserver.domain.photo.service.PhotoService;
import com.pophory.pophoryserver.domain.pophorysm.dto.request.PophorysmShareRequestDto;
import com.pophory.pophoryserver.domain.s3.S3Service;
import com.pophory.pophoryserver.domain.s3.UploadType;
import com.pophory.pophoryserver.domain.version.dto.response.PophorysmGetPresignedUrlResponseDto;
import com.pophory.pophoryserver.global.util.MemberUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v2/pophorysm")
@Tag(name = "[Pophorysm] 포포리즘 관련 API")
public class PophorysmController {

    private final PophorysmService pophorysmService;

    @GetMapping(produces = APPLICATION_JSON_VALUE)
    @Operation(summary = "포포리즘 공유를 위한 presigned url 조회")
    @Parameter(name = "Authorization", description = "Bearer {access_token}", in = ParameterIn.HEADER, required = true, schema = @Schema(type = "string"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "포포리즘 공유를 위한 presigned url 조회 성공"),
            @ApiResponse(responseCode = "400", description = "presigned url 조회 실패"),
            @ApiResponse(responseCode = "500", description = "서버 오류")
    })
    public ResponseEntity<PophorysmGetPresignedUrlResponseDto> getPophorysmPresignedUrl(@RequestParam("u") String nickname, Principal principal) {
        return ResponseEntity.ok(pophorysmService.getSharePresignedUrl(UploadType.PHOTO, nickname, MemberUtil.getMemberId(principal)));
    }

    @PostMapping(consumes = APPLICATION_JSON_VALUE)
    @Operation(summary = "포포리즘 공유 API")
    @Parameter(name = "Authorization", description = "Bearer {access_token}", in = ParameterIn.HEADER, required = true, schema = @Schema(type = "string"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "포포리즘 공유 성공"),
            @ApiResponse(responseCode = "400", description = "포포리즘 공유 실패"),
            @ApiResponse(responseCode = "500", description = "서버 오류")
    })
    public ResponseEntity<Void> sharePhotoByPophorysm(@RequestBody PophorysmShareRequestDto request, Principal principal) {
        pophorysmService.sharePhoto(request, MemberUtil. getMemberId(principal));
        return ResponseEntity.ok().build();
    }
}

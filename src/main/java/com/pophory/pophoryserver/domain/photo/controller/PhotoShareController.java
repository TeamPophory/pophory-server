package com.pophory.pophoryserver.domain.photo.controller;


import com.pophory.pophoryserver.domain.photo.dto.response.PhotoShareApproveResponseDto;
import com.pophory.pophoryserver.domain.photo.dto.response.PhotoShareResponseDto;
import com.pophory.pophoryserver.domain.photo.service.PhotoShareService;
import com.pophory.pophoryserver.global.util.MemberUtil;
import com.pophory.pophoryserver.global.util.PhotoUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v2/share/")
@Tag(name = "[Photo] 네컷사진 공유 관련 API (V2)")
public class PhotoShareController {
    
    private final PhotoShareService photoShareService;
    @GetMapping("{shareId}")
    @Operation(summary = "공유한 사진 정보 조회 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "공유할 사진 정보 조회 성공"),
            @ApiResponse(responseCode = "400", description = "공유할 사진 정보 조회 실패", content = @Content),
            @ApiResponse(responseCode = "500", description = "서버 오류", content = @Content)
    })
    public ResponseEntity<PhotoShareResponseDto> getSharedPhoto(@PathVariable String shareId) {
        return ResponseEntity.ok(photoShareService.getSharedPhoto(shareId));
    }

    @PostMapping("/photo/{photoId}")
    @SecurityRequirement(name = "Authorization")
    @Parameter(name = "Authorization", description = "Bearer {access_token}", in = ParameterIn.HEADER, required = true, schema = @Schema(type = "string"))
    @Operation(summary = "공유 수락 API", description = "공유 수락 버튼을 눌렀을 때, 사용자의 앨범에 사진이 추가됩니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "사진 공유 성공"),
            @ApiResponse(responseCode = "400", description = "사진 공유 실패", content = @Content),
            @ApiResponse(responseCode = "500", description = "서버 오류", content = @Content)
    })
    public ResponseEntity<PhotoShareApproveResponseDto> approveSharedPhoto(@PathVariable("photoId") Long sharedPhotoId, Principal principal) {
        return ResponseEntity.ok(photoShareService.addPhotoFromShare(MemberUtil.getMemberId(principal), sharedPhotoId));
    }
}

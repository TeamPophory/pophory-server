package com.pophory.pophoryapi.photo.presentation;

import com.pophory.pophoryapi.photo.application.PhotoService;
import com.pophory.pophoryapi.photo.dto.request.PhotoAddV1RequestDto;
import com.pophory.pophorycommon.util.MemberUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import java.security.Principal;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.http.MediaType.MULTIPART_FORM_DATA_VALUE;

@RestController
@RequestMapping("/api/v1/photo")
@RequiredArgsConstructor
@Tag(name = "[Photo] 네컷사진 관련 API (V1)")
@SecurityRequirement(name = "Authorization")
public class PhotoV1Controller {

    private final PhotoService photoService;

    @PostMapping(consumes = MULTIPART_FORM_DATA_VALUE, produces = APPLICATION_JSON_VALUE)
    @Operation(summary = "사진 추가 API")
    @Parameter(name = "Authorization", description = "Bearer {access_token}", in = ParameterIn.HEADER, required = true, schema = @Schema(type = "string"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "사진 추가 성공"),
            @ApiResponse(responseCode = "400", description = "사진 추가 실패", content = @Content),
            @ApiResponse(responseCode = "500", description = "서버 오류", content = @Content)
    })
    public ResponseEntity<Void> addPhoto(
            @Schema(description = "사진 file") @RequestPart MultipartFile photo,
            @Valid PhotoAddV1RequestDto request, Principal principal) {
        photoService.addPhotoV1(photo, request, MemberUtil.getMemberId(principal));
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping(path = "/{photoId}")
    @Operation(summary = "사진 삭제 API")
    @Parameter(name = "Authorization", description = "Bearer {access_token}", in = ParameterIn.HEADER, required = true, schema = @Schema(type = "string"))
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "204", description = "사진 삭제 성공"),
                    @ApiResponse(responseCode = "400", description = "사진 삭제 실패", content = @Content),
                    @ApiResponse(responseCode = "404", description = "해당 사진이 존재하지 않습니다.", content = @Content),
                    @ApiResponse(responseCode = "500", description = "서버 오류", content = @Content)
            }
    )
    public ResponseEntity<Void> deletePhoto(@PathVariable Long photoId, Principal principal) {
        photoService.deletePhoto(photoId, MemberUtil.getMemberId(principal));
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}

package com.pophory.pophoryapi.photo.presentation;


import com.pophory.pophoryapi.photo.application.PhotoService;

import com.pophory.pophoryapi.photo.dto.request.PhotoAddV2RequestDto;
import com.pophory.pophoryapi.photo.dto.response.PhotoAddResponseDto;
import com.pophory.pophoryapi.photo.dto.response.PhotoAllListResponseDto;
import com.pophory.pophorycommon.util.MemberUtil;
import com.pophory.pophorycommon.util.PhotoUtil;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URISyntaxException;
import java.security.Principal;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v2/photo")
@Tag(name = "[Photo] 네컷사진 관련 API (V2)")
@SecurityRequirement(name = "Authorization")
public class PhotoV2Controller {

    private final PhotoService photoService;

    @PostMapping(consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    @Operation(summary = "사진 추가 API")
    @Parameter(name = "Authorization", description = "Bearer {access_token}", in = ParameterIn.HEADER, required = true, schema = @Schema(type = "string"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "사진 추가 성공"),
            @ApiResponse(responseCode = "400", description = "사진 추가 실패", content = @Content),
            @ApiResponse(responseCode = "500", description = "서버 오류", content = @Content)
    })
    public ResponseEntity<Void> uploadPhoto(@RequestBody PhotoAddV2RequestDto request, Principal principal) throws URISyntaxException {
        PhotoAddResponseDto response =  photoService.addPhotoV2(request, MemberUtil.getMemberId(principal));
        return ResponseEntity.created(PhotoUtil.getURI(response.getPhotoId())).build();
    }

    @DeleteMapping( "/{photoId}")
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

    public ResponseEntity<PhotoAllListResponseDto> getAllPhoto(Principal principal) {
        return ResponseEntity.ok(photoService.getAllPhotosV2(MemberUtil.getMemberId(principal)));
    }

    @GetMapping
    @Operation(summary = "사진 전체 조회 API")
    @Parameter(name = "Authorization", description = "Bearer {access_token}", in = ParameterIn.HEADER, required = true, schema = @Schema(type = "string"))
    @ApiResponses( value = {
            @ApiResponse(responseCode = "200", description = "사진 전체 조회 성공"),
            @ApiResponse(responseCode = "400", description = "사진 전체 조회 실패", content = @Content),
            @ApiResponse(responseCode = "500", description = "서버 오류", content = @Content)
    }
    )
    public ResponseEntity<PhotoAllListResponseDto> getAllPhotos(Principal principal) {
        return ResponseEntity.ok(photoService.getAllPhotosV2(MemberUtil.getMemberId(principal)));
    }
}

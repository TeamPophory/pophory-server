package com.pophory.pophoryserver.domain.photo;

import com.pophory.pophoryserver.domain.photo.dto.request.PhotoAddRequestDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.http.MediaType.MULTIPART_FORM_DATA_VALUE;

@RestController
@RequestMapping("/api/v1/photo")
@RequiredArgsConstructor
@Tag(name = "Photo", description = "사진 관련 API")
@SecurityRequirement(name = "Authorization")
public class PhotoController {

    private final PhotoService photoService;

    @PostMapping(consumes = MULTIPART_FORM_DATA_VALUE, produces = APPLICATION_JSON_VALUE)
    @Operation(summary = "사진 추가 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "사진 추가 성공"),
            @ApiResponse(responseCode = "400", description = "사진 추가 실패"),
            @ApiResponse(responseCode = "500", description = "서버 오류")
    })
    public ResponseEntity<Void> addPhoto(
            @Schema(description = "사진 file") @RequestPart MultipartFile photo,
            @Valid PhotoAddRequestDto request, @RequestHeader Long memberId) {
        photoService.addPhoto(photo, request, memberId);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping(path = "/{photoId}")
    @Operation(summary = "사진 삭제 API")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "204", description = "사진 삭제 성공"),
                    @ApiResponse(responseCode = "400", description = "사진 삭제 실패"),
                    @ApiResponse(responseCode = "404", description = "해당 사진이 존재하지 않습니다."),
                    @ApiResponse(responseCode = "500", description = "서버 오류")
            }
    )
    public ResponseEntity<Void> deletePhoto(@PathVariable Long photoId, @RequestHeader Long memberId) {
        photoService.deletePhoto(photoId, memberId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}

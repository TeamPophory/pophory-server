package com.pophory.pophoryapi.album.presentation;


import com.pophory.pophoryapi.album.application.AlbumService;
import com.pophory.pophoryapi.album.dto.request.AlbumDesignUpdateRequestDto;
import com.pophory.pophoryapi.album.dto.response.AlbumGetV2ResponseDto;
import com.pophory.pophoryapi.album.dto.response.AlbumListGetV2ResponseDto;
import com.pophory.pophoryapi.photo.dto.response.PhotoListGetV2ResponseDto;
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

import java.security.Principal;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v2/album")
@Tag(name = "[Album] 앨범 관련 API (V2)")
@SecurityRequirement(name = "Authorization")
public class AlbumV2Controller {

    private final AlbumService albumService;

    @GetMapping
    @Operation(summary = "앨범 목록 조회 API")
    @Parameter(name = "Authorization", description = "Bearer {access_token}", in = ParameterIn.HEADER, required = true, schema = @Schema(type = "string"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "앨범 목록 조회 성공"),
            @ApiResponse(responseCode = "400", description = "앨범 목록 조회 실패", content = @Content),
            @ApiResponse(responseCode = "500", description = "서버 오류", content = @Content)
    })
    public ResponseEntity<AlbumListGetV2ResponseDto> getAlbums(Principal principal) {
        return ResponseEntity.ok(albumService.getAlbumsV2(MemberUtil.getMemberId(principal)));
    }

    @GetMapping("/{albumId}")
    @Operation(summary = "앨범 단건 조회 API")
    @Parameter(name = "Authorization", description = "Bearer {access_token}", in = ParameterIn.HEADER, required = true, schema = @Schema(type = "string"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "앨범 목록 조회 성공"),
            @ApiResponse(responseCode = "400", description = "앨범 목록 조회 실패", content = @Content),
            @ApiResponse(responseCode = "500", description = "서버 오류", content = @Content)
    })
    public ResponseEntity<AlbumGetV2ResponseDto> getAlbumById(@PathVariable Long albumId) {
        return ResponseEntity.ok(albumService.getAlbum(albumId));
    }

    @PatchMapping("/{albumId}")
    @Operation(summary = "앨범 디자인 수정 API")
    @Parameter(name = "Authorization", description = "Bearer {access_token}", in = ParameterIn.HEADER, required = true, schema = @Schema(type = "string"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "앨범 디자인 수정 성공"),
            @ApiResponse(responseCode = "400", description = "앨범 디자인 수정 실패", content = @Content),
            @ApiResponse(responseCode = "500", description = "서버 오류", content = @Content)
    })
    public ResponseEntity<Void> updateAlbumDesign(@PathVariable Long albumId, @Valid @RequestBody AlbumDesignUpdateRequestDto request) {
        albumService.updateAlbumDesign(request, albumId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
    
    @GetMapping("/{albumId}/photo")
    @Operation(summary = "앨범 내 사진 목록 조회 API")
    @Parameter(name = "Authorization", description = "Bearer {access_token}", in = ParameterIn.HEADER, required = true, schema = @Schema(type = "string"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "앨범 내 사진 목록 조회 성공"),
            @ApiResponse(responseCode = "400", description = "앨범 내 사진 목록 조회 실패", content = @Content),
            @ApiResponse(responseCode = "500", description = "서버 오류", content = @Content)
    })
    public ResponseEntity<PhotoListGetV2ResponseDto> getAlbumPhotoById(@PathVariable Long albumId, Principal principal) {
        return ResponseEntity.ok(albumService.getPhotosByAlbumV2(albumId, MemberUtil.getMemberId(principal)));
    }
}
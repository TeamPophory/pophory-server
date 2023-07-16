package com.pophory.pophoryserver.domain.album.controller;


import com.pophory.pophoryserver.domain.album.AlbumService;
import com.pophory.pophoryserver.domain.album.dto.response.AlbumGetResponseDto;
import com.pophory.pophoryserver.domain.album.dto.response.AlbumListGetResponseDto;
import com.pophory.pophoryserver.domain.photo.dto.response.PhotoListGetResponseDto;
import com.pophory.pophoryserver.global.util.MemberUtil;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v2/albums")
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
    public ResponseEntity<AlbumListGetResponseDto> getAlbums(Principal principal) {
        return ResponseEntity.ok(albumService.getAlbums(MemberUtil.getMemberId(principal)));
    }

    @GetMapping("/{albumId}")
    @Operation(summary = "앨범 단건 조회 API")
    @Parameter(name = "Authorization", description = "Bearer {access_token}", in = ParameterIn.HEADER, required = true, schema = @Schema(type = "string"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "앨범 목록 조회 성공"),
            @ApiResponse(responseCode = "400", description = "앨범 목록 조회 실패", content = @Content),
            @ApiResponse(responseCode = "500", description = "서버 오류", content = @Content)
    })
    public ResponseEntity<AlbumGetResponseDto> getAlbumById(@PathVariable Long albumId) {
        return ResponseEntity.ok(albumService.getAlbum(albumId));
    }
    
    @GetMapping("/{albumId}/photo")
    @Operation(summary = "앨범 내 사진 목록 조회 API")
    @Parameter(name = "Authorization", description = "Bearer {access_token}", in = ParameterIn.HEADER, required = true, schema = @Schema(type = "string"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "앨범 내 사진 목록 조회 성공"),
            @ApiResponse(responseCode = "400", description = "앨범 내 사진 목록 조회 실패", content = @Content),
            @ApiResponse(responseCode = "500", description = "서버 오류", content = @Content)
    })
    public ResponseEntity<PhotoListGetResponseDto> getAlbumPhotoById(@PathVariable Long albumId, Principal principal) {
        return ResponseEntity.ok(albumService.getPhotosByAlbum(albumId, MemberUtil.getMemberId(principal)));
    }
}
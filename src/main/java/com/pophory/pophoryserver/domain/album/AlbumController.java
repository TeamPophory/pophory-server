package com.pophory.pophoryserver.domain.album;

import com.pophory.pophoryserver.domain.album.dto.response.AlbumListGetResponseDto;
import com.pophory.pophoryserver.domain.photo.dto.response.PhotoGetResponseDto;
import com.pophory.pophoryserver.domain.photo.dto.response.PhotoListGetResponseDto;
import com.pophory.pophoryserver.global.util.MemberUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

import static com.pophory.pophoryserver.global.util.MemberUtil.getMemberId;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/albums")
@Tag(name = "album", description = "앨범 관련 API")
@SecurityRequirement(name = "Authorization")
public class AlbumController {

    private final AlbumService albumService;

    @GetMapping
    @Operation(summary = "앨범 목록 조회 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "앨범 목록 조회 성공"),
            @ApiResponse(responseCode = "400", description = "앨범 목록 조회 실패", content = @Content),
            @ApiResponse(responseCode = "500", description = "서버 오류", content = @Content)
    })
    public ResponseEntity<AlbumListGetResponseDto> getAlbums(Principal principal) {
        return ResponseEntity.ok(albumService.getAlbums(getMemberId(principal)));
    }

    @GetMapping("{albumId}/photos")
    @Operation(summary = "앨범에 속한 사진 목록 조회 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "앨범에 속한 사진 목록 조회 성공"),
            @ApiResponse(responseCode = "400", description = "앨범에 속한 사진 목록 조회 실패", content = @Content),
            @ApiResponse(responseCode = "404", description = "앨범이 존재하지 않습니다.", content = @Content),
            @ApiResponse(responseCode = "500", description = "서버 오류", content = @Content)
    })
    public ResponseEntity<PhotoListGetResponseDto> getPhotos(@PathVariable Long albumId, Principal principal) {
        return ResponseEntity.ok(albumService.getPhotosByAlbum(albumId, getMemberId(principal)));
    }

}

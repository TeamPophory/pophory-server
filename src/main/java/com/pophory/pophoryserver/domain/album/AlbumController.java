package com.pophory.pophoryserver.domain.album;

import com.pophory.pophoryserver.domain.album.dto.response.AlbumListGetResponseDto;
import com.pophory.pophoryserver.domain.photo.dto.response.PhotoListGetResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/albums")
public class AlbumController {

    private final AlbumService albumService;

    @GetMapping
    public ResponseEntity<AlbumListGetResponseDto> getAlbums(@RequestHeader Long memberId) {
        return ResponseEntity.ok(albumService.getAlbums(memberId));
    }

    @GetMapping("{albumId}/photos")
    public ResponseEntity<PhotoListGetResponseDto> getPhotos(
            @PathVariable Long albumId,
            @RequestHeader Long memberId) {
        return ResponseEntity.ok(albumService.getPhotosByAlbum(albumId, memberId));
    }

}

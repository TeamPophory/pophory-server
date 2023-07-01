package com.pophory.pophoryserver.domain.album;

import com.pophory.pophoryserver.domain.album.dto.response.AlbumListGetResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/albums")
public class AlbumController {

    private final AlbumService albumService;

    @GetMapping
    public ResponseEntity<AlbumListGetResponseDto> getAlbums(@RequestHeader Long memberId) {
        return ResponseEntity.ok(albumService.getAlbums(memberId));
    }

}

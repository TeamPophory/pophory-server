package com.pophory.pophoryserver.domain.photo;

import com.pophory.pophoryserver.domain.photo.dto.request.PhotoAddRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/photo")
@RequiredArgsConstructor
public class PhotoController {

    private final PhotoService photoService;

    @PostMapping
    public ResponseEntity<Void> addPhoto(@RequestPart MultipartFile photo, @Valid PhotoAddRequestDto request, @RequestHeader Long memberId) {
        photoService.addPhoto(photo, request, memberId);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("/{photoId}")
    public ResponseEntity<Void> deletePhoto(@PathVariable Long photoId, @RequestHeader Long memberId) {
        photoService.deletePhoto(photoId, memberId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}

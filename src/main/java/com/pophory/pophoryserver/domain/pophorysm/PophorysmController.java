package com.pophory.pophoryserver.domain.pophorysm;

import com.pophory.pophoryserver.domain.photo.service.PhotoService;
import com.pophory.pophoryserver.domain.pophorysm.dto.request.PophorysmShareRequestDto;
import com.pophory.pophoryserver.domain.s3.S3Service;
import com.pophory.pophoryserver.domain.s3.UploadType;
import com.pophory.pophoryserver.domain.version.dto.response.PophorysmGetPresignedUrlResponseDto;
import com.pophory.pophoryserver.global.util.MemberUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v2/pophorysm")
public class PophorysmController {

    private final PophorysmService pophorysmService;

    @GetMapping
    public ResponseEntity<PophorysmGetPresignedUrlResponseDto> getPophorysmPresignedUrl(@RequestParam("u") String nickname, Principal principal) {
        return ResponseEntity.ok(pophorysmService.getSharePresignedUrl(UploadType.PHOTO, nickname, MemberUtil.getMemberId(principal)));
    }

    @PostMapping
    public ResponseEntity<Void> sharePhotoByPophorysm(@RequestBody PophorysmShareRequestDto request, Principal principal) {
        pophorysmService.sharePhoto(request, MemberUtil.getMemberId(principal));
        return ResponseEntity.ok().build();
    }
}

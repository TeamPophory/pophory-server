package com.pophory.pophoryserver.domain.studio;

import com.pophory.pophoryserver.domain.studio.dto.StudioResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/studios")
public class StudioController {

    private final StudioService studioService;

    @GetMapping
    public ResponseEntity<StudioResponseDto> getAllStudio() {
        return ResponseEntity.ok(studioService.findAll());
    }
}

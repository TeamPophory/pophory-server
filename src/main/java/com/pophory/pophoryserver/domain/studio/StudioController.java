package com.pophory.pophoryserver.domain.studio;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/studios")
public class StudioController {

    private final StudioService studioService;

    @GetMapping
    public ResponseEntity<StudioResponseDTO> getAllStudio() {
        StudioResponseDTO allStudio = studioService.findAll();

        return ResponseEntity.ok(allStudio);
    }
}

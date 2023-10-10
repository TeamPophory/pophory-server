package com.pophory.pophoryserver.domain.advertisement;


import com.pophory.pophoryserver.domain.advertisement.dto.response.AdvertisementResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v2/ad")
public class AdvertisementV2Controller {

    private final AdvertisementService advertisementService;

    @GetMapping
    public ResponseEntity<List<AdvertisementResponse>> getAdvertisements(@RequestParam String os, @RequestParam String version) {
        return ResponseEntity.ok(advertisementService.getAdvertisements(os, version));
    }

}

package com.pophory.pophoryserver.domain.advertisement;


import com.pophory.pophoryserver.domain.advertisement.dto.response.AdvertisementResponse;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v2/ad")
@Tag(name = "[Advertisement] 광고 관련 API (V2)")
@SecurityRequirement(name = "Authorization")
public class AdvertisementV2Controller {

    private final AdvertisementService advertisementService;

    @GetMapping
    @Operation(summary = "광고 목록 조회 API")
    @Parameter(name = "Authorization", description = "Bearer {access_token}", in = ParameterIn.HEADER, required = true, schema = @Schema(type = "string"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "광고 목록 조회 성공"),
            @ApiResponse(responseCode = "400", description = "앨범 목록 조회 실패", content = @Content),
            @ApiResponse(responseCode = "500", description = "서버 오류", content = @Content)
    })
    public ResponseEntity<List<AdvertisementResponse>> getAdvertisements(@RequestParam String os, @RequestParam String version) {
        return ResponseEntity.ok(advertisementService.getAdvertisements(os, version));
    }

}

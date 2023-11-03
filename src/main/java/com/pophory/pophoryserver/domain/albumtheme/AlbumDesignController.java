package com.pophory.pophoryserver.domain.albumtheme;


import com.pophory.pophoryserver.domain.albumtheme.dto.response.AlbumDesignListGetResponseDto;
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
import org.springframework.web.bind.annotation.RestController;

import static io.swagger.v3.oas.annotations.enums.ParameterIn.*;

@RestController
@RequiredArgsConstructor
@Tag(name = "[AlbumDesign] 앨범 디자인 관련 API (V2)")
@RequestMapping("/api/v2/album/design")
@SecurityRequirement(name = "Authorization")
public class AlbumDesignController {

    private final AlbumDesignService albumDesignService;

    @GetMapping
    @Operation(summary = "앨범 디자인 정보 조회 API")
    @Parameter(name = "Authorization", description = "Bearer {access_token}", in = HEADER, required = true, schema = @Schema(type = "string"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = " 앨범 디자인 정보 조회 성공"),
            @ApiResponse(responseCode = "400", description = "사진 추가 실패", content = @Content),
            @ApiResponse(responseCode = "500", description = "서버 오류", content = @Content)
    })
    public ResponseEntity<AlbumDesignListGetResponseDto> getAlbumDesignList() {
        return ResponseEntity.ok(albumDesignService.getAlbumDesignList());
    }
}

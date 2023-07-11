package com.pophory.pophoryserver.domain.studio;

import com.pophory.pophoryserver.domain.studio.dto.StudioResponseDto;
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

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/studios")
@Tag(name = "[Studio] 사진관 관련 API")
@SecurityRequirement(name = "Authorization")
public class StudioController {

    private final StudioService studioService;

    @GetMapping
    @Operation(summary = "스튜디오 목록 조회 API")
    @Parameter(name = "Authorization", description = "Bearer {access_token}", in = ParameterIn.HEADER, required = true, schema = @Schema(type = "string"))
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "스튜디오 목록 조회 성공"),
                    @ApiResponse(responseCode = "400", description = "스튜디오 목록 조회 실패", content = @Content),
                    @ApiResponse(responseCode = "500", description = "서버 오류", content = @Content)
            }
    )
    public ResponseEntity<StudioResponseDto> getAllStudio() {return ResponseEntity.ok(studioService.findAll());}
}

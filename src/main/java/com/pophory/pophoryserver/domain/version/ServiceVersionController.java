package com.pophory.pophoryserver.domain.version;


import com.pophory.pophoryserver.domain.version.dto.VersionListGetResponseDto;
import com.pophory.pophoryserver.domain.version.dto.VersionUpdateRequestDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/version")
@Tag(name = "[Service Version] 서비스 버전 관리 (V2)")
public class ServiceVersionController {

    private final ServiceVersionService serviceVersionService;

    @GetMapping
    @Operation(summary = "Version 조회 API")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Version 조회 성공"),
                    @ApiResponse(responseCode = "400", description = "Version 조회 실패", content = @Content),
                    @ApiResponse(responseCode = "500", description = "서버 오류", content = @Content)
            }
    )
    public ResponseEntity<VersionListGetResponseDto> getAppVersion() {
        return ResponseEntity.ok(serviceVersionService.getVersion());
    }

    @PutMapping
    @Operation(summary = "Version 업데이트 API")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "204", description = "Version 업데이트 성공"),
                    @ApiResponse(responseCode = "400", description = "Version 업데이트 실패", content = @Content),
                    @ApiResponse(responseCode = "500", description = "서버 오류", content = @Content)
            }
    )
    public ResponseEntity<Void> updateAppVersion(@RequestBody VersionUpdateRequestDto request) {
        serviceVersionService.updateServiceVersion(request);
        return ResponseEntity.noContent().build();
    }
}
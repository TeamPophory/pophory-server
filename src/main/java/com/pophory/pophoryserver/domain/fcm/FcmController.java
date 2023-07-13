package com.pophory.pophoryserver.domain.fcm;

import com.pophory.pophoryserver.domain.fcm.dto.request.FcmRequestDto;
import com.pophory.pophoryserver.global.util.MemberUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.security.Principal;

@RestController
@RequestMapping("/api/v2")
@RequiredArgsConstructor
@Tag(name = "[Fcm] Firebase Cloud Message 관련 API")
public class FcmController {

    private final FcmService fcmService;

    @Deprecated
    @PostMapping("/push/test")
    @Operation(summary = "푸쉬 알림 Test API")
    @Parameter(name = "Authorization", description = "Bearer {access_token}", in = ParameterIn.HEADER, required = true, schema = @Schema(type = "string"))
    @ApiResponses( value = {
            @ApiResponse(responseCode = "200", description = "푸쉬 알림 성공"),
            @ApiResponse(responseCode = "500", description = "서버 오류", content = @Content)
    })
    public ResponseEntity<Void> pushTest(Principal principal, @RequestBody FcmRequestDto request) throws IOException {
        fcmService.sendFcm(MemberUtil.getMemberId(principal), request);
        return ResponseEntity.noContent().build();
    }
}

package com.pophory.pophoryserver.domain.member.auth;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
@Tag(name = "auth", description = "사용자 인증/인가 관련 API")
public class AuthController {

    private final AuthService authService;

    @DeleteMapping(produces = "application/json")
    @SecurityRequirement(name = "Authorization")
    @Operation(summary = "회원탈퇴 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "회원탈퇴 성공"),
            @ApiResponse(responseCode = "400", description = "회원탈퇴 실패"),
            @ApiResponse(responseCode = "500", description = "서버 오류")
    })
    public ResponseEntity<Void> signOut(@RequestHeader Long memberId) {
        authService.signOut(memberId);
        return ResponseEntity.noContent().build();
    }
}

package com.pophory.pophoryserver.domain.member.auth;

import com.pophory.pophoryserver.domain.member.auth.dto.request.AuthRequestDto;
import com.pophory.pophoryserver.domain.member.auth.dto.response.AuthResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
@Tag(name = "auth", description = "사용자 인증/인가 관련 API")
public class AuthController {

    private final AuthService authService;
    private final SocialService socialService;

    @DeleteMapping(produces = "application/json")
    @SecurityRequirement(name = "Authorization")
    @Operation(summary = "회원탈퇴 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "회원탈퇴 성공"),
            @ApiResponse(responseCode = "400", description = "회원탈퇴 실패", content = @Content),
            @ApiResponse(responseCode = "500", description = "서버 오류", content = @Content)
    })
    public ResponseEntity<Void> signOut(@RequestHeader Long memberId) {
        authService.signOut(memberId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping
    @SecurityRequirement(name = "Authorization")
    @Operation(summary = "소셜로그인 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "소셜로그인 성공"),
            @ApiResponse(responseCode = "400", description = "소셜로그인 실패", content = @Content),
            @ApiResponse(responseCode = "500", description = "서버 오류", content = @Content)
    })
    public ResponseEntity<AuthResponseDto> socialLogin(@RequestHeader("Authorization") String socialAccessToken, @RequestBody AuthRequestDto authRequestDto) throws NoSuchAlgorithmException, InvalidKeySpecException {
        return ResponseEntity.ok(socialService.signIn(socialAccessToken, authRequestDto));
    }
}

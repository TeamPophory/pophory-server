package com.pophory.pophoryserver.domain.auth.controller;

import com.pophory.pophoryserver.domain.auth.AuthService;
import com.pophory.pophoryserver.domain.auth.SocialService;
import com.pophory.pophoryserver.domain.auth.dto.request.AuthRequestDto;
import com.pophory.pophoryserver.domain.auth.dto.response.AuthResponseDto;
import com.pophory.pophoryserver.domain.auth.dto.response.TokenResponseDto;
import com.pophory.pophoryserver.global.util.MemberUtil;
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
import org.springframework.web.bind.annotation.*;

import java.security.NoSuchAlgorithmException;
import java.security.Principal;
import java.security.spec.InvalidKeySpecException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v2/auth")
@Tag(name = "[Auth] 사용자 인증/인가 관련 API (V2)")
public class AuthV2Controller {

    private final SocialService socialService;
    private final AuthService authService;

    @PostMapping
    @SecurityRequirement(name = "Authorization")
    @Operation(summary = "소셜로그인 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "소셜로그인 성공"),
            @ApiResponse(responseCode = "400", description = "소셜로그인 실패", content = @Content),
            @ApiResponse(responseCode = "500", description = "서버 오류", content = @Content)
    })
    public ResponseEntity<AuthResponseDto> socialLogin(@RequestHeader("Authorization") String socialAccessToken, @RequestBody AuthRequestDto request) throws NoSuchAlgorithmException, InvalidKeySpecException {
        return ResponseEntity.ok(socialService.signIn(socialAccessToken, request));
    }
}

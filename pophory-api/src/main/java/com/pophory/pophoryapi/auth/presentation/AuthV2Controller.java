package com.pophory.pophoryapi.auth.presentation;


import com.pophory.pophoryapi.auth.application.AuthService;
import com.pophory.pophoryapi.auth.application.SocialService;
import com.pophory.pophoryapi.auth.dto.request.AuthRequestDto;
import com.pophory.pophoryapi.auth.dto.response.AuthResponseDto;
import com.pophory.pophoryapi.auth.dto.response.TokenResponseDto;
import com.pophory.pophorycommon.util.MemberUtil;
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
    @Operation(summary = "소셜로그인 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "소셜로그인 성공"),
            @ApiResponse(responseCode = "400", description = "소셜로그인 실패", content = @Content),
            @ApiResponse(responseCode = "500", description = "서버 오류", content = @Content)
    })
    public ResponseEntity<AuthResponseDto> socialLogin(@RequestHeader("Authorization") String socialAccessToken, @RequestBody AuthRequestDto request) throws NoSuchAlgorithmException, InvalidKeySpecException {
        return ResponseEntity.ok(socialService.signIn(socialAccessToken, request));
    }

    @PostMapping("/token")
    @SecurityRequirement(name = "Authorization")
    @Operation(summary = "토큰 재발급 API")
    @Parameter(name = "Authorization", description = "Bearer {access_token}", in = ParameterIn.HEADER, schema = @Schema(type = "string"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "토큰 재발급 성공"),
            @ApiResponse(responseCode = "400", description = "토큰 재발급 실패", content = @Content),
            @ApiResponse(responseCode = "500", description = "서버 오류", content = @Content)
    })
    public ResponseEntity<TokenResponseDto> reissue(Principal principal) {
        return ResponseEntity.ok(authService.reIssue(MemberUtil.getMemberId(principal)));
    }


    @DeleteMapping(produces = "application/json")
    @SecurityRequirement(name = "Authorization")
    @Operation(summary = "회원탈퇴 API")
    @Parameter(name = "Authorization", description = "Bearer {access_token}", in = ParameterIn.HEADER, required = true, schema = @Schema(type = "string"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "회원탈퇴 성공"),
            @ApiResponse(responseCode = "400", description = "회원탈퇴 실패", content = @Content),
            @ApiResponse(responseCode = "500", description = "서버 오류", content = @Content)
    })
    public ResponseEntity<Void> signOut(Principal principal) {
        authService.signOut(MemberUtil.getMemberId(principal));
        return ResponseEntity.noContent().build();
    }
}
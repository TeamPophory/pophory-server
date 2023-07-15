package com.pophory.pophoryserver.domain.member.controller;


import com.pophory.pophoryserver.domain.member.MemberService;
import com.pophory.pophoryserver.domain.member.dto.response.MemberGetResponseDto;
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

import java.security.Principal;

import static com.pophory.pophoryserver.global.util.MemberUtil.getMemberId;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v2/member")
@Tag(name = "[Member] 사용자 관련 API (V2)")
@SecurityRequirement(name = "Authorization")
public class MemberV2Controller {

    private final MemberService memberService;
    @GetMapping("/me")
    @Operation(summary = "사용자 정보 조회 API")
    @Parameter(name = "Authorization", description = "Bearer {access_token}", in = ParameterIn.HEADER, required = true, schema = @Schema(type = "string"))
    @ApiResponses( value = {
            @ApiResponse(responseCode = "200", description = "사용자 정보 조회 성공"),
            @ApiResponse(responseCode = "400", description = "사용자 정보 조회 실패", content = @Content),
            @ApiResponse(responseCode = "500", description = "서버 오류", content = @Content)
    }
    )
    public ResponseEntity<MemberGetResponseDto> getMember(Principal principal) {
        return ResponseEntity.ok(memberService.getMember(getMemberId(principal)));
    }
}

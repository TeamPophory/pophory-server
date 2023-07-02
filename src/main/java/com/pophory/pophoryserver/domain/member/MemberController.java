package com.pophory.pophoryserver.domain.member;

import com.pophory.pophoryserver.domain.member.dto.request.MemberCreateRequestDto;
import com.pophory.pophoryserver.domain.member.dto.response.MemberGetResponseDto;
import com.pophory.pophoryserver.domain.member.dto.response.MemberMyPageGetResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/member")
@Tag(name = "Member", description = "사용자 관련 API")
@SecurityRequirement(name = "Authorization")
public class MemberController {
    public final MemberService memberService;

    @PatchMapping
    @Operation(summary = "회원가입 API")
    @ApiResponses( value = {
            @ApiResponse(responseCode = "204", description = "회원가입 성공"),
            @ApiResponse(responseCode = "400", description = "회원가입 실패"),
            @ApiResponse(responseCode = "500", description = "서버 오류")
    }
    )
    public ResponseEntity<Void> patchMember(@Valid @RequestBody MemberCreateRequestDto memberCreateRequestDto, @RequestHeader Long memberId) {
        memberService.update(memberCreateRequestDto, memberId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    @Operation(summary = "마이페이지 조회 API")
    @ApiResponses( value = {
            @ApiResponse(responseCode = "200", description = "마이페이지 조회 성공"),
            @ApiResponse(responseCode = "400", description = "마이페이지 조회 실패"),
            @ApiResponse(responseCode = "500", description = "서버 오류")
    }
    )
    public ResponseEntity<MemberMyPageGetResponseDto> getMypageMember(@RequestHeader Long memberId) {
        return ResponseEntity.ok(memberService.getMypageMember(memberId));
    }

    @GetMapping("/me")
    @Operation(summary = "사용자 정보 조회 API")
    @ApiResponses( value = {
            @ApiResponse(responseCode = "200", description = "사용자 정보 조회 성공"),
            @ApiResponse(responseCode = "400", description = "사용자 정보 조회 실패"),
            @ApiResponse(responseCode = "500", description = "서버 오류")
    }
    )
    public ResponseEntity<MemberGetResponseDto> getMember(@RequestHeader Long memberId) {
        return ResponseEntity.ok(memberService.getMember(memberId));
    }
}

package com.pophory.pophoryapi.member.presentation;

import com.pophory.pophoryapi.member.application.MemberService;
import com.pophory.pophoryapi.member.dto.request.MemberCreateV2RequestDto;
import com.pophory.pophoryapi.member.dto.request.MemberNicknameDuplicateRequestDto;
import com.pophory.pophoryapi.member.dto.response.MemberCreateResponseDto;
import com.pophory.pophoryapi.member.dto.response.MemberGetResponseDto;
import com.pophory.pophoryapi.member.dto.response.MemberMyPageGetV2ResponseDto;
import com.pophory.pophoryapi.member.dto.response.MemberNicknameDuplicateResponseDto;
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
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

import static com.pophory.pophorycommon.util.MemberUtil.getMemberId;
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v2/member")
@Tag(name = "[Member] 사용자 관련 API (V2)")
@SecurityRequirement(name = "Authorization")
public class MemberV2Controller {

    private final MemberService memberService;

    @PatchMapping
    @Operation(summary = "회원가입 API")
    @Parameter(name = "Authorization", description = "Bearer {access_token}", in = ParameterIn.HEADER, required = true, schema = @Schema(type = "string"))
    @ApiResponses( value = {
            @ApiResponse(responseCode = "204", description = "회원가입 성공"),
            @ApiResponse(responseCode = "400", description = "회원가입 실패", content = @Content),
            @ApiResponse(responseCode = "500", description = "서버 오류", content = @Content)
    }
    )
    public ResponseEntity<MemberCreateResponseDto> patchMember(@Valid @RequestBody MemberCreateV2RequestDto request, Principal principal) {
        return ResponseEntity.ok(memberService.updateV2(request, getMemberId(principal)));
    }

    @GetMapping
    @Operation(summary = "마이페이지 내 정보 조회 API")
    @Parameter(name = "Authorization", description = "Bearer {access_token}", in = ParameterIn.HEADER, required = true, schema = @Schema(type = "string"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "내 정보 조회 성공"),
            @ApiResponse(responseCode = "400", description = "내 정보 조회 실패", content = @Content),
            @ApiResponse(responseCode = "500", description = "서버 오류", content = @Content)
    }
    )
    public ResponseEntity<MemberMyPageGetV2ResponseDto> getMemberV2(Principal principal) {
        return ResponseEntity.ok(memberService.getMypageMemberV2(getMemberId(principal)));
    }


    @GetMapping("/me")
    @Operation(summary = "사용자 정보 조회 API")
    @Parameter(name = "Authorization", description = "Bearer {access_token}", in = ParameterIn.HEADER, required = true, schema = @Schema(type = "string"))
    @ApiResponses( value = {
            @ApiResponse(responseCode = "200", description = "사용자 정보 조회 성공"),
            @ApiResponse(responseCode = "400", description = "사용자 정보 조회 실패", content = @Content),
            @ApiResponse(responseCode = "500", description = "서버 오류", content = @Content)
    })
    public ResponseEntity<MemberGetResponseDto> getMember(Principal principal) {
        return ResponseEntity.ok(memberService.getMember(getMemberId(principal)));
    }

    @PostMapping
    @Operation(summary = "멤버 아이디 중복 조회 API")
    @Parameter(name = "Authorization", description = "Bearer {access_token}", in = ParameterIn.HEADER, required = true, schema = @Schema(type = "string"))
    @ApiResponses( value = {
            @ApiResponse(responseCode = "200", description = "아이디 중복 조회 성공"),
            @ApiResponse(responseCode = "400", description = "사용자 정보 조회 실패", content = @Content),
            @ApiResponse(responseCode = "500", description = "서버 오류", content = @Content)
    }
    )
    public ResponseEntity<MemberNicknameDuplicateResponseDto> postMemberNickname(@Valid @RequestBody MemberNicknameDuplicateRequestDto request) {
        return ResponseEntity.ok(memberService.checkDuplicateMemberNickname(request.getNickname()));
    }

    @PostMapping("/sign-out")
    @Operation(summary = "멤버 로그아웃 API")
    @Parameter(name = "Authorization", description = "Bearer {access_token}", in = ParameterIn.HEADER, required = true, schema = @Schema(type = "string"))
    @ApiResponses( value = {
            @ApiResponse(responseCode = "204", description = "로그아웃 성공"),
            @ApiResponse(responseCode = "400", description = "로그아웃 실패", content = @Content),
            @ApiResponse(responseCode = "500", description = "서버 오류", content = @Content)
    }
    )
    public ResponseEntity<Void> logout(Principal principal){
        memberService.logout(MemberUtil.getMemberId(principal));
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}

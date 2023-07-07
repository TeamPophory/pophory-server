package com.pophory.pophoryserver.domain.member;

import com.pophory.pophoryserver.domain.member.dto.request.MemberCreateRequestDto;
import com.pophory.pophoryserver.domain.member.dto.request.MemberNicknameDuplicateRequestDto;
import com.pophory.pophoryserver.domain.member.dto.response.MemberGetResponseDto;
import com.pophory.pophoryserver.domain.member.dto.response.MemberMyPageGetResponseDto;
import com.pophory.pophoryserver.domain.member.dto.response.MemberNicknameDuplicateResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;

import static com.pophory.pophoryserver.global.util.MemberUtil.getMemberId;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/member")
@Tag(name = "Member", description = "사용자 관련 API")
@SecurityRequirement(name = "Authorization")
public class MemberController {
    private final MemberService memberService;

    @PatchMapping
    @Operation(summary = "회원가입 API")
    @ApiResponses( value = {
            @ApiResponse(responseCode = "204", description = "회원가입 성공"),
            @ApiResponse(responseCode = "400", description = "회원가입 실패", content = @Content),
            @ApiResponse(responseCode = "500", description = "서버 오류", content = @Content)
    }
    )
    public ResponseEntity<Void> patchMember(@Valid @RequestBody MemberCreateRequestDto memberCreateRequestDto, Principal principal) {
        memberService.update(memberCreateRequestDto, getMemberId(principal));
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    @Operation(summary = "마이페이지 조회 API")
    @ApiResponses( value = {
            @ApiResponse(responseCode = "200", description = "마이페이지 조회 성공"),
            @ApiResponse(responseCode = "400", description = "마이페이지 조회 실패", content = @Content),
            @ApiResponse(responseCode = "500", description = "서버 오류", content = @Content)
    }
    )
    public ResponseEntity<MemberMyPageGetResponseDto> getMypageMember(Principal principal) {
        return ResponseEntity.ok(memberService.getMypageMember(getMemberId(principal)));
    }

    @GetMapping("/me")
    @Operation(summary = "사용자 정보 조회 API")
    @ApiResponses( value = {
            @ApiResponse(responseCode = "200", description = "사용자 정보 조회 성공"),
            @ApiResponse(responseCode = "400", description = "사용자 정보 조회 실패", content = @Content),
            @ApiResponse(responseCode = "500", description = "서버 오류", content = @Content)
    }
    )
    public ResponseEntity<MemberGetResponseDto> getMember(Principal principal) {
        return ResponseEntity.ok(memberService.getMember(getMemberId(principal)));
    }

    @PostMapping
    @Operation(summary = "멤버 아이디 중복 조회 API")
    @ApiResponses( value = {
            @ApiResponse(responseCode = "200", description = "아이디 중복 조회 성공"),
            @ApiResponse(responseCode = "400", description = "사용자 정보 조회 실패", content = @Content),
            @ApiResponse(responseCode = "500", description = "서버 오류", content = @Content)
    }
    )
    public ResponseEntity<MemberNicknameDuplicateResponseDto> postMemberNickname(@Valid @RequestBody MemberNicknameDuplicateRequestDto memberNicknameDuplicateRequestDto) {
        return ResponseEntity.ok(memberService.checkDuplicateMemberNickname(memberNicknameDuplicateRequestDto.getNickname()));
    }
}

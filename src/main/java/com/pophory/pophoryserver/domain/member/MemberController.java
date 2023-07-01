package com.pophory.pophoryserver.domain.member;

import com.pophory.pophoryserver.domain.member.dto.request.MemberCreateRequestDto;
import com.pophory.pophoryserver.domain.member.dto.response.MemberGetResponseDto;
import com.pophory.pophoryserver.domain.member.dto.response.MemberMyPageGetResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/member")
public class MemberController {
    public final MemberService memberService;

    @PatchMapping
    public ResponseEntity<Void> patchMember(@Valid @RequestBody MemberCreateRequestDto memberCreateRequestDto, @RequestHeader Long memberId) {
        memberService.update(memberCreateRequestDto, memberId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<MemberMyPageGetResponseDto> getMypageMember(@RequestHeader Long memberId) {
        return ResponseEntity.ok(memberService.getMypageMember(memberId));
    }

    @GetMapping("/me")
    public ResponseEntity<MemberGetResponseDto> getMember(@RequestHeader Long memberId) {
        return ResponseEntity.ok(memberService.getMember(memberId));
    }
}

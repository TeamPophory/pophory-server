package com.pophory.pophoryserver.domain.member;

import com.pophory.pophoryserver.domain.member.dto.request.MemberCreateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/member")
public class MemberController {
    public final MemberService memberService;

    @PatchMapping
    public ResponseEntity.BodyBuilder patchMember(MemberCreateRequestDto memberCreateRequestDto) {
        memberService.update(memberCreateRequestDto);
        return ResponseEntity.status(HttpStatus.NO_CONTENT);
    }
}

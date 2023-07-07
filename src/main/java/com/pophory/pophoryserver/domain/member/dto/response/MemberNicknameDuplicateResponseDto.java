package com.pophory.pophoryserver.domain.member.dto.response;

import com.pophory.pophoryserver.domain.member.Member;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Schema(description = "회원 아이디 중복 여부")
public class MemberNicknameDuplicateResponseDto {
    @Schema(description = "회원 아이디 중복 여부", example = "false")
    private Boolean isDuplicated;

    public static MemberNicknameDuplicateResponseDto of(Boolean isDuplicated) {
        return new MemberNicknameDuplicateResponseDto(isDuplicated);
    }
}

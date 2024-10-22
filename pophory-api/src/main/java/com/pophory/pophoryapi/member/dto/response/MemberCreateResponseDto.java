package com.pophory.pophoryapi.member.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

import static lombok.AccessLevel.PRIVATE;

@AllArgsConstructor(access = PRIVATE)
@Getter
@Schema(description = "회원가입")
public class MemberCreateResponseDto {
    @Schema(description = "앨범 아이디", example = "1")
    private Long albumId;

    public static MemberCreateResponseDto of(Long albumId) {
        return new MemberCreateResponseDto(albumId);
    }
}

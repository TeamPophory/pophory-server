package com.pophory.pophoryserver.domain.member.dto.request;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MemberCreateRequestDto {

    @NotBlank
    @Size(min = 1, max = 10)
    @Pattern(regexp = "[가-힣]")
    private String realName;

    @NotBlank
    @Size(min = 4, max = 12)
    @Pattern(regexp = "\\w$")
    private String nickname;

    @NotBlank
    private int albumCover;
}

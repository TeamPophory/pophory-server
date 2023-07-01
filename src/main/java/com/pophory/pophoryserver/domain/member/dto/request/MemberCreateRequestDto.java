package com.pophory.pophoryserver.domain.member.dto.request;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
public class MemberCreateRequestDto {

    @Size(min = 1, max = 10)
    @Pattern(regexp = "^[가-힣]*$")
    private String realName;

    @Size(min = 4, max = 12)
    @Pattern(regexp = "^[a-zA-Z0-9_]*$")
    private String nickname;

    private int albumCover;
}

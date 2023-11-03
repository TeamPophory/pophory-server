package com.pophory.pophoryapi.member.dto.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;


@Getter
@Schema(description = "회원 아이디 중복 확인 요청 DTO")
public class MemberNicknameDuplicateRequestDto {

    @NotBlank
    @Size(min = 4, max = 12)
    @Pattern(regexp = "^[a-zA-Z0-9_]*$")
    @Schema(description = "회원 아이디", example = "pophory")
    private String nickname;

    @JsonCreator
    private MemberNicknameDuplicateRequestDto(String nickname) {
        this.nickname = nickname;
    }
}

package com.pophory.pophoryserver.domain.photo.dto.response;

import com.pophory.pophoryserver.domain.member.Member;
import com.pophory.pophoryserver.domain.photo.Photo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PhotoShareResponseDto {

    @Schema(description = "사용자 실명", example = "장혜린")
    private String realName;

    @Schema(description = "사용자 아이디", example = "l.___.in")
    private String nickname;

    @Schema(description = "공유하는 사진 id", example = "1")
    private Long photoId;

    @Schema(description = "공유하는 사진 url", example = "https://~")
    private String imageUrl;

    public static PhotoShareResponseDto of(Photo photo, Member member) {
        return new PhotoShareResponseDto(member.getRealName(), member.getNickname(), photo.getId(), photo.getImageUrl());
    }
}

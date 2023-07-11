package com.pophory.pophoryserver.domain.fcm.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotNull;

@AllArgsConstructor
@Getter
public class FcmRequestDto {
    @Schema(description = "제목", example = "포포리 푸쉬 알림 title")
    @NotNull
    private String title;

    @Schema(description = "제목", example = "포포리 푸쉬 알림 body")
    @NotNull
    private String body;
}

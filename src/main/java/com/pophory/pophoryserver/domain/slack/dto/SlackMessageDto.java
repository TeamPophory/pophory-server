package com.pophory.pophoryserver.domain.slack.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor(staticName = "of")
@Data
public class SlackMessageDto {
    private String text;
}

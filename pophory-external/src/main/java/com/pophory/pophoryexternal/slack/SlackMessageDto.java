package com.pophory.pophoryexternal.slack;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor(staticName = "of")
@Data
public class SlackMessageDto {
    private String text;
}

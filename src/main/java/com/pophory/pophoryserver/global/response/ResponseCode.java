package com.pophory.pophoryserver.global.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ResponseCode {
    ALBUM_LIMIT_EXCEED(442);

    private final int code;
}

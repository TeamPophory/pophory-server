package com.pophory.pophorycommon.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ResponseCode {
    ALBUM_LIMIT_EXCEED(442),
    SELF_APPROVE(4423);

    private final int code;
}

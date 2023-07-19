package com.pophory.pophoryserver.global.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@Getter
@RequiredArgsConstructor
public class CodeResponse {
    @JsonInclude(NON_NULL)
    private final long code;
}

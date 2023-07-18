package com.pophory.pophoryserver.global.util;

import java.security.Principal;
import java.security.SecureRandom;
import java.util.Objects;

public class MemberUtil {
    public static Long getMemberId(Principal principal) {
        if (Objects.isNull(principal)) throw new IllegalArgumentException("principal is null");
        return Long.valueOf(principal.getName());
    }


}

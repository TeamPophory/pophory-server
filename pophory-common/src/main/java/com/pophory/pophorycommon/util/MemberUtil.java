package com.pophory.pophorycommon.util;

import java.security.Principal;
import java.util.Objects;

public class MemberUtil {
    public static Long getMemberId(Principal principal) {
        if (Objects.isNull(principal)) throw new IllegalArgumentException("principal is null");
        return Long.valueOf(principal.getName());
    }


}

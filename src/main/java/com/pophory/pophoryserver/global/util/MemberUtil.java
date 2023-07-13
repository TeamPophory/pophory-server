package com.pophory.pophoryserver.global.util;

import java.security.Principal;
import java.security.SecureRandom;
import java.util.Objects;

public class MemberUtil {
    private static final SecureRandom RANDOM = new SecureRandom();
    private static final String CHARACTERS = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";

    public static Long getMemberId(Principal principal) {
        if (Objects.isNull(principal)) throw new IllegalArgumentException("principal is null");
        return Long.valueOf(principal.getName());
    }

    public static String generateRandomString(int length) {
        StringBuilder builder = new StringBuilder(length);

        for (int i = 0; i < length; i++) {
            int randomIndex = RANDOM.nextInt(CHARACTERS.length());
            builder.append(CHARACTERS.charAt(randomIndex));
        }

        return builder.toString();
    }
}

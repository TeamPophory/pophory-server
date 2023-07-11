package com.pophory.pophoryserver.domain.fcm.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class FcmDto {
    boolean validateOnly;
    MessageDto message;

    public static FcmDto of (MessageDto message) {
        return new FcmDto(false, message);
    }
}

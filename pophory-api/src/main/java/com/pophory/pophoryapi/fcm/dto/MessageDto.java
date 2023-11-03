package com.pophory.pophoryapi.fcm.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MessageDto {
    NotificationDto notification;
    String token;
}

package com.pophory.pophoryapi.fcm.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class NotificationDto {
    private String title;
    private String body;
}

package com.pophory.pophoryserver.domain.message;

import org.springframework.data.annotation.CreatedDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity
public class Message {

    @Id @GeneratedValue
    private Long id;
    private Long senderId;
    private Long receiverId;
    private String imageUrl;

    @CreatedDate
    private LocalDateTime createdAt;
}

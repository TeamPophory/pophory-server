package com.pophory.pophoryserver.domain.tag;

import com.pophory.pophoryserver.domain.photo.Photo;

import javax.persistence.*;

import static javax.persistence.FetchType.*;

@Entity
public class GuestTag {

    @Id @GeneratedValue
    private Long id;

    @JoinColumn(name = "photo_id")
    @ManyToOne(fetch = LAZY)
    private Photo photo;

    private String name;
}

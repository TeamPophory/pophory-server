package com.pophory.pophoryserver.domain.studio;

import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@NoArgsConstructor
public class Studio {

    @Id @GeneratedValue
    private Long id;

    @Column(unique = true, nullable = false)
    private String name;

    private String imageUrl;
}
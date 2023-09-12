package com.pophory.pophoryserver.domain.studio;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Entity
@Getter
@NoArgsConstructor
public class Studio {

    @Id @GeneratedValue
    private Long id;

    @NotNull
    @Column(unique = true)
    private String name;

    private String imageUrl;

    @Builder
    public Studio(String name, String imageUrl) {
        this.name = name;
        this.imageUrl = imageUrl;
    }
}

package com.pophory.pophoryserver.domain.studio;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Entity
@Getter
@NoArgsConstructor
public class Studio {

    @Id
    @GeneratedValue
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

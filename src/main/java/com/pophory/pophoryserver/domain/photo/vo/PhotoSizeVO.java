package com.pophory.pophoryserver.domain.photo.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(staticName = "of")
public class PhotoSizeVO {

    private Integer width;
    private Integer height;
}
